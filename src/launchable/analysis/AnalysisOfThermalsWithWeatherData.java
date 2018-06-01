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
import org.bridj.ann.Runtime;
import util.igc.RemoveDuplicate;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;
import visualization.javaFxMaps.GenerateJSSettings;
import visualization.javaFxMaps.GenerateJson;
import model.geography.Longitude;
import model.geography.Latitude;

import javax.management.RuntimeErrorException;
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


        System.out.println("thermal weather analysis start:");
        try {
            analyse(flights, outputData);
        } catch (InvalidCoordinatesException e) {
            System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
            e.printStackTrace();
        }
        System.out.println("Count thermal no weather analysis start:");
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

            if (1 == 0) {

                throw new RuntimeException("make kenneth make them unique before joining with the weather");
            }

            // looping through every thermal group
            for (ThermalDataPointGroup grp : dataGroup) {
                List<DataPoint> dataPoints = grp.getGroup();

                for (DataPoint point : dataPoints) {
                    tmpWeatherRecord = weatherFactory.getWeather(flight.getDate(), point.getTime(),point );
                    thermalIgcJoinWeathers.add(new IGCJoinWeather(point, tmpWeatherRecord));
                }

            }

            //finding unique indexes in thermals for flight and registering
            List<IGCJoinWeather> unique = RemoveDuplicate.getUniqueByGridIndex(thermalIgcJoinWeathers);
            int windDirectionDegree;
            int latIndex;
            int longIndex;
            for (IGCJoinWeather item : unique) {
                try {
                    latIndex = item.getDataPoint().getLatitude().getGridIndex();
                    longIndex = item.getDataPoint().getLongitude().getGridIndex();
                    windDirectionDegree = item.getWeatherRecord().getWind().getWindDirection().getDegree().getDegree();
                    if (windDirectionDegree >= 0 && windDirectionDegree < 90) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirZeroToNinty().incrementRegisteredThermal();
                    }else
                    if (windDirectionDegree >= 90 && windDirectionDegree < 180) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirNintyToHundredEighty().incrementRegisteredThermal();
                    }else
                    if (windDirectionDegree >= 180 && windDirectionDegree < 270) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirHundredEightyToTwoHundredSeventy().incrementRegisteredThermal();
                    }else
                    if (windDirectionDegree >= 270 && windDirectionDegree < 360) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirTwoHundredSeventyToThreeHundredSizty().incrementRegisteredThermal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //find unique data points from data in flights
            List<DataPoint> uniqueDataPoints = RemoveDuplicate.getUniqueDataPoint(flight.getDatalog());
            List<IGCJoinWeather> joinWeathers = new ArrayList<>();
            IGCJoinWeather igcJoinWeather;
            for (DataPoint dataPoint : uniqueDataPoints) {
                igcJoinWeather = new IGCJoinWeather(dataPoint, weatherFactory.getWeather(flight.getDate(), dataPoint.getTime(), dataPoint));
                joinWeathers.add(igcJoinWeather);
            }

            //add all the unique join with weather as flights
            for (IGCJoinWeather item : joinWeathers) {
                try {
                    latIndex = item.getDataPoint().getLatitude().getGridIndex();
                    longIndex = item.getDataPoint().getLongitude().getGridIndex();
                    windDirectionDegree = item.getWeatherRecord().getWind().getWindDirection().getDegree().getDegree();
                    if (windDirectionDegree >= 0 && windDirectionDegree < 90) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirZeroToNinty().incrementRegisteredFlight();
                    }else
                    if (windDirectionDegree >= 90 && windDirectionDegree < 180) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirNintyToHundredEighty().incrementRegisteredFlight();
                    }else
                    if (windDirectionDegree >= 180 && windDirectionDegree < 270) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirHundredEightyToTwoHundredSeventy().incrementRegisteredFlight();
                    }else
                    if (windDirectionDegree >= 270 && windDirectionDegree < 360) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirTwoHundredSeventyToThreeHundredSizty().incrementRegisteredFlight();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//
            }


        }
    }
}
