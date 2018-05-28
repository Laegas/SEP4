package launchable.analysis;

import model.IGCJoinWeather;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import model.outputData.IGCDataGroup;
import model.outputData.OutputData;
import model.weather.WeatherFactory;
import model.weather.WeatherRecord;
import util.flight.FlightSplitter;
import util.flight.FlightSplitterImp;
import util.igc.RemoveDuplicate;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by kenneth on 24/05/2018.
 */
public class AnalysisOfThermalsWithWeatherData {
    public static void main(String[] args) {

    }

    public static void analyse(List<Flight> flights, OutputData outputData) {

        // join all the data points with the weather for each flight

        ThermalFinder thermalFinder = new ThermalFinderImp();
        WeatherFactory weatherFactory = WeatherFactory.getINSTANCE();

        //looping through all flights
        for (Flight flight : flights) {

            //getting a list of thermal data point groups from the thermal finder
            List<ThermalDataPointGroup> dataGroup = thermalFinder.findThermalsUsingGPSAltitude(flight);
            // data storage for weather data
            List<IGCJoinWeather> thermalIgcJoinWeathers = new ArrayList<>();
            WeatherRecord tmpWeatherRecord;

            // looping through every thermal group
            for (ThermalDataPointGroup grp : dataGroup) {
                List<DataPoint> dataPoints = grp.getGroup();
                DataPoint[] sorted = dataPoints.toArray(new DataPoint[dataPoints.size()]);
                Arrays.sort(sorted);

                for (DataPoint point : dataPoints) {
                    tmpWeatherRecord = weatherFactory.getWeather(flight.getDate(), point.getTime());
                    thermalIgcJoinWeathers.add(new IGCJoinWeather(point, tmpWeatherRecord));
                }

            }

            //loopin through all data points to register flihts at indexes with temperature
            for (DataPoint dataPoint : flight.getDatalog()) {
                //join with weather
            }

            //finding unique indexes in this flight and registering the thermals
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
        }




    }
}
