package database.DAO;

import model.geography.Latitude;
import model.geography.Longitude;
import model.time.Date;
import model.time.Time;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;

import java.util.List;

/**
 * Created by kenneth on 24/05/2018.
 */
public interface WeatherDimensionalDao {
    /**
     * temp solution
     * @param date
     * @return
     */
    public List<WeatherRecord> getWeatherRecord(Date date, ICAOAirportCode airportCode);
//      implement when we store airport codes and locations in database
//    public WeatherRecord getWeatherRecord(Date date, Time time, Longitude longitude, Latitude latitude);
}
