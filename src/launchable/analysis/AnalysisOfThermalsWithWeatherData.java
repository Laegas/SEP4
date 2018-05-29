package launchable.analysis;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.IGCJoinWeather;
import model.geography.InvalidCoordinatesException;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import model.outputData.OutputData;
import model.weather.WeatherFactory;
import model.weather.WeatherRecord;
import util.igc.RemoveDuplicate;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;
import visualization.javaFxMaps.GenerateJSSettings;
import visualization.javaFxMaps.GenerateJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 24/05/2018.
 */
public class AnalysisOfThermalsWithWeatherData {
    public static void main(String[] args) {

        OutputData outputData = new OutputData();
        GenerateJson generateJson = GenerateJson.getInstance();

        IGCDimensionalDao igcDimensionalDao = DaoManager.IGC_DIMENSIONAL_DAO;

        List<Flight> flights = igcDimensionalDao.getAllFlights();
        System.out.println("number of flights: " + flights.size());


        System.out.println("thermal weather analasys start:");
        try {
            analyse(flights, outputData);
        } catch (InvalidCoordinatesException e) {
            System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
            e.printStackTrace();
        }
        System.out.println("Count thermal no weather analasys start:");
        try {
            CountThermalsAnalysis.analyse(flights, outputData);
        } catch (InvalidCoordinatesException e) {
            System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
            e.printStackTrace();
        }

        generateJson.generateJson(outputData);
        GenerateJSSettings.getInstance().generateSettings(outputData);
    }

    /**
     * this method will change the output data object
     * @param flights
     * @param outputData
     */
    public static void analyse(List<Flight> flights, OutputData outputData) throws InvalidCoordinatesException {

        // join all the data points with the weather for each flight

        ThermalFinder thermalFinder = new ThermalFinderImp(1);
        WeatherFactory weatherFactory = WeatherFactory.getINSTANCE();

        //looping through all flights
        int analysedCounter = 0;
        for (Flight flight : flights) {
            System.out.println("analysing flight #" + analysedCounter++);
//            getting a list of thermal data point groups from the thermal finder
            List<ThermalDataPointGroup> dataGroup = thermalFinder.findThermalsUsingGPSAltitude(flight);
            // data storage for weather data
            List<IGCJoinWeather> thermalIgcJoinWeathers = new ArrayList<>();
            WeatherRecord tmpWeatherRecord;

            // looping through every thermal group
            for (ThermalDataPointGroup grp : dataGroup) {
                List<DataPoint> dataPoints = grp.getGroup();

                for (DataPoint point : dataPoints) {
                    tmpWeatherRecord = weatherFactory.getWeather(flight.getDate(), point.getTime(),point.getLongitude(),point.getLatitude() );
                    thermalIgcJoinWeathers.add(new IGCJoinWeather(point, tmpWeatherRecord));
                }

            }

            //finding unique indexes in thermals for flight and registering
            List<IGCJoinWeather> unique = RemoveDuplicate.getUniqueByGridIndex(thermalIgcJoinWeathers);
            double tempTenperature;
            for (IGCJoinWeather item : unique) {
                tempTenperature = item.getWeatherRecord().getTemperature().getTemperature();
                int latIndex = item.getDataPoint().getLatitude().getGridIndex();
                int longIndex = item.getDataPoint().getLongitude().getGridIndex();
                if (tempTenperature >= 0 && tempTenperature < 10) {
                    outputData.getFeatureProperties(latIndex,longIndex).getBetweenZeroAndTenDegree().incrementRegisteredThermal();
                } else if (tempTenperature >= 10 && tempTenperature < 20) {
                    outputData.getFeatureProperties(latIndex,longIndex).getBetweenTenAndTwentyDegree().incrementRegisteredThermal();
                } else if (tempTenperature >= 20 && tempTenperature < 30) {
                    outputData.getFeatureProperties(latIndex,longIndex).getBetweenTwentyAndThirtyDegree().incrementRegisteredThermal();
                }
            }

            //find unique data points from data in flights
            List<DataPoint> uniqueDataPoints = RemoveDuplicate.getUniqueDataPoint(flight.getDatalog());
            List<IGCJoinWeather> joinWeathers = new ArrayList<>();
            IGCJoinWeather igcJoinWeather;
            for (DataPoint dataPoint : uniqueDataPoints) {
                igcJoinWeather = new IGCJoinWeather(dataPoint, weatherFactory.getWeather(flight.getDate(), dataPoint.getTime(), dataPoint.getLongitude(), dataPoint.getLatitude()));
                joinWeathers.add(igcJoinWeather);
            }

            //add all the unique join with weather as flights
            for (IGCJoinWeather item : joinWeathers) {

                double tempTemperature = item.getWeatherRecord().getTemperature().getTemperature();
                int latIndex = item.getDataPoint().getLatitude().getGridIndex();
                int longIndex = item.getDataPoint().getLongitude().getGridIndex();

                if (tempTemperature >= 0 && tempTemperature < 10) {
                    outputData.getFeatureProperties(latIndex, longIndex).getBetweenZeroAndTenDegree().incrementRegisteredFlight();
                } else if (tempTemperature >= 10 && tempTemperature < 20) {
                    outputData.getFeatureProperties(latIndex, longIndex).getBetweenTenAndTwentyDegree().incrementRegisteredFlight();
                } else if (tempTemperature >= 20 && tempTemperature < 30) {
                    outputData.getFeatureProperties(latIndex, longIndex).getBetweenTwentyAndThirtyDegree().incrementRegisteredFlight();
                }
            }


        }
    }
}
