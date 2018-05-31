package model.weather;

import database.DAO.DaoManager;
import database.DAO.WeatherDimensionalDao;
import model.geography.Latitude;
import model.geography.Longitude;
import model.time.Date;
import model.time.Time;
import util.geography.GeoCaluclator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 24/05/2018.
 */
public class WeatherFactory {
    private static final WeatherFactory INSTANCE = new WeatherFactory();

    private WeatherDimensionalDao weatherDimensionalDao;
    private Map<String, List<WeatherRecord>> localWeatherRecordByDateAndICAOCache;


    private WeatherFactory() {
        // TODO MAKE STUFF WHEN THE WEATHER DAO IS IMPLEMENTED
        weatherDimensionalDao = DaoManager.WEATHER_DIMENSIONAL_DAO;
        this.localWeatherRecordByDateAndICAOCache = new HashMap<>();
    }

    public static WeatherFactory getINSTANCE() {
        return INSTANCE;
    }

    /**
     * should get the airport code using longitude and latitude and then call the other getWeather method
     *
     * @param date
     * @param time
     * @param longitude
     * @param latitude
     * @return
     */
    public WeatherRecord getWeather(Date date, Time time, Longitude longitude, Latitude latitude) {
        // get using latitude and longitude
        ICAOAirportCode airport_code = getClosestAirort(latitude, longitude);
        if (airport_code != null) {

            return getWeather(date, time, airport_code);
        }

        return null;

    }

    /**
     * it is prefered to use the closesAirpor reference from igc in the database
     * @param latitude
     * @param longitude
     * @return
     */
    @Deprecated
    private ICAOAirportCode getClosestAirort(Latitude latitude, Longitude longitude) {

        List<Airport> airports = DaoManager.WEATHER_DIMENSIONAL_DAO.getAirportsByDate(new Date(1, 1,1));

        double shortestDistance = Double.MAX_VALUE;
        Airport resultPointer = null;

        double tempDist = 0;
        for (Airport airport : airports) {
            tempDist = GeoCaluclator.getGeoDistance(latitude, longitude, airport.getLatitude(), airport.getLongitude());
            if (tempDist < shortestDistance) {
                resultPointer = airport;
            }
        }

        try {
            return resultPointer.getAirport();
        } catch (NullPointerException e) {
            return null;
        }

    }


    private WeatherRecord getWeather(Date date, Time time, ICAOAirportCode airportCode) {
        //EKAH is the airport code for Aarhus CURRENT DEFAULT
        ICAOAirportCode icao_in_use = airportCode;

        // cache system for weather data maped by a string representing the date
        List<WeatherRecord> weatherRecords = localWeatherRecordByDateAndICAOCache.get(getLocalCacheString(date, airportCode));
        if (weatherRecords == null) {
            weatherRecords = this.weatherDimensionalDao.getWeatherRecord(date, icao_in_use);
            localWeatherRecordByDateAndICAOCache.put(getLocalCacheString(date, airportCode), weatherRecords);
        }

        WeatherRecord result = findClosestWeatherRecordUsingTime(weatherRecords, time);

        return result;
    }

    private static WeatherRecord findClosestWeatherRecordUsingTime(List<WeatherRecord> records, Time time) {
        int distanceInSeconds = Integer.MAX_VALUE;
        WeatherRecord resultPointer = null;

        Time item_time;
        int temp_time_between = 0;
        for (WeatherRecord item : records) {
            item_time = new Time(item.getHour().getHour(), item.getMinute().getMinute(), 0);
            temp_time_between = time.timeBetween(item_time).getTotalSeconds();

            if (temp_time_between < distanceInSeconds) {
                distanceInSeconds = temp_time_between;
                resultPointer = item;
                continue;
            }
        }

        return resultPointer;
    }

    private static String getLocalCacheString(Date date , ICAOAirportCode airportCode) {
        return (date.getDay().getDayOfMonth() + ":" + date.getMonth().getMonthNumber() +
                ":" + date.getYear().getYear()) + ":" + airportCode.getICAOCode();
    }
}
