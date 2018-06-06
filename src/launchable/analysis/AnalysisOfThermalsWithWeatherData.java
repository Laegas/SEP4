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

    private static final boolean VERBOSE = true;
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
            e.printStackTrace();
        }
        System.out.println("Count thermal no weather analysis start:");
        try {
            CountThermalsAnalysis.analyse(flights, outputData);
        } catch (InvalidCoordinatesException e) {
            e.printStackTrace();
        }

        int featureCount = generateJson.generateJson(outputData);
        GenerateJSSettings.getInstance().generateSettings(outputData, featureCount);
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
            WeatherRecord tmpWeatherRecord;

            // looping through every thermal group
            List<DataPoint> uniqueDataPoint = new ArrayList<>();
            for (ThermalDataPointGroup grp : dataGroup) {
                List<DataPoint> dataPoints = grp.getGroup();

                for (DataPoint tmpDataPoint : dataPoints) {
                    uniqueDataPoint.add(tmpDataPoint);
                }
            }

            uniqueDataPoint = RemoveDuplicate.getUniqueDataPoint(uniqueDataPoint);


            List<IGCJoinWeather> thermalIgcJoinWeathers = new ArrayList<>();
            for (DataPoint point : uniqueDataPoint) {
                tmpWeatherRecord = weatherFactory.getWeather(flight.getDate(), point.getTime(),point );
                thermalIgcJoinWeathers.add(new IGCJoinWeather(point, tmpWeatherRecord));
            }
            if (VERBOSE)
                System.out.println("finished joining with weather");

            int windDirectionDegree;
            int latIndex;
            int longIndex;
            for (IGCJoinWeather item : thermalIgcJoinWeathers) {
                try {
                    latIndex = item.getDataPoint().getLatitude().getGridIndex();
                    longIndex = item.getDataPoint().getLongitude().getGridIndex();
                    windDirectionDegree = item.getWeatherRecord().getWind().getWindDirection().getDegree().getDegree();
                    if (windDirectionDegree >= 0 && windDirectionDegree < 90) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirZeroToNinety().incrementRegisteredThermal();
                    }else
                    if (windDirectionDegree >= 90 && windDirectionDegree < 180) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirNinetyToHundredEighty().incrementRegisteredThermal();
                    }else
                    if (windDirectionDegree >= 180 && windDirectionDegree < 270) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirHundredEightyToTwoHundredSeventy().incrementRegisteredThermal();
                    }else
                    if (windDirectionDegree >= 270 && windDirectionDegree < 360) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirTwoHundredSeventyToThreeHundredSixty().incrementRegisteredThermal();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (VERBOSE) {
                System.out.println("finished adding thermals to output data");
            }
            //find unique data points from data in flights
            List<DataPoint> uniqueDataPoints = RemoveDuplicate.getUniqueDataPoint(flight.getDatalog());
            List<IGCJoinWeather> joinWeathers = new ArrayList<>();
            IGCJoinWeather igcJoinWeather;
            if (VERBOSE)
                System.out.println("found all the unique grid features");
            for (DataPoint dataPoint : uniqueDataPoints) {
                igcJoinWeather = new IGCJoinWeather(dataPoint, weatherFactory.getWeather(flight.getDate(), dataPoint.getTime(), dataPoint));
                joinWeathers.add(igcJoinWeather);
            }
            if (VERBOSE)
                System.out.println("finished finding unique flight grid features");

            //add all the unique join with weather as flights
            for (IGCJoinWeather item : joinWeathers) {
                try {
                    latIndex = item.getDataPoint().getLatitude().getGridIndex();
                    longIndex = item.getDataPoint().getLongitude().getGridIndex();
                    windDirectionDegree = item.getWeatherRecord().getWind().getWindDirection().getDegree().getDegree();
                    if (windDirectionDegree >= 0 && windDirectionDegree < 90) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirZeroToNinety().incrementRegisteredFlight();
                    }else
                    if (windDirectionDegree >= 90 && windDirectionDegree < 180) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirNinetyToHundredEighty().incrementRegisteredFlight();
                    }else
                    if (windDirectionDegree >= 180 && windDirectionDegree < 270) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirHundredEightyToTwoHundredSeventy().incrementRegisteredFlight();
                    }else
                    if (windDirectionDegree >= 270 && windDirectionDegree < 360) {
                        outputData.getFeatureProperties(latIndex,longIndex).getWindDirTwoHundredSeventyToThreeHundredSixty().incrementRegisteredFlight();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//
            }

        }
    }
}
