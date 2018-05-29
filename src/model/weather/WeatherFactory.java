package model.weather;

import database.DAO.DaoManager;
import database.DAO.WeatherDimensionalDao;
import model.geography.Latitude;
import model.geography.Longitude;
import model.time.Date;
import model.time.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 24/05/2018.
 */
public class WeatherFactory {
    private static final WeatherFactory INSTANCE = new WeatherFactory();

    private WeatherDimensionalDao weatherDimensionalDao;
    private Map<String, List<WeatherRecord>> localWeatherRecoedByDateCache;


    private WeatherFactory() {
        // TODO MAKE STUFF WHEN THE WEATHER DAO IS IMPLEMENTED
        weatherDimensionalDao = DaoManager.WEATHER_DIMENSIONAL_DAO;
        localWeatherRecoedByDateCache = new HashMap<>();
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
        ICAOAirportCode airport_code = new ICAOAirportCode("");

        return getWeather(date, time, airport_code);

    }

    public WeatherRecord getWeather(Date date, Time time, ICAOAirportCode airportCode) {
        // input airport code not currently used
        //EKAH is the airport code for Aarhus CURRENT DEFAULT
        ICAOAirportCode icao_in_use = new ICAOAirportCode("EKAH");


        // cache system for weather data maped by a string representing the date
        List<WeatherRecord> weatherRecords = localWeatherRecoedByDateCache.get(getLocalCacheString(date));
        if (weatherRecords == null) {
            weatherRecords = this.weatherDimensionalDao.getWeatherRecord(date, icao_in_use);
            localWeatherRecoedByDateCache.put(getLocalCacheString(date), weatherRecords);
        }


//        System.out.println(time);
//        System.out.println(date);

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

    private static String getLocalCacheString(Date date) {
        return (date.getDay().getDayOfMonth() + ":" + date.getMonth().getMonthNumber() + ":" + date.getYear().getYear());
    }
}
