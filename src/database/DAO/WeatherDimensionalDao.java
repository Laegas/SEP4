package database.DAO;

import model.geography.Latitude;
import model.geography.Longitude;
import model.time.Date;
import model.time.Time;
import model.weather.Airport;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;

import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 24/05/2018.
 */
public interface WeatherDimensionalDao {
    /**
     * temp solution
     * @param date
     * @return
     */
    List<WeatherRecord> getWeatherRecord(Date date, ICAOAirportCode airportCode);
//      implement when we store airport codes and locations in database
//    public WeatherRecord getWeatherRecord(Date date, Time time, Longitude longitude, Latitude latitude);
    Airport getAirportByDateAndICAOCode(ICAOAirportCode code, Date date);
    List<Airport> getAirportsByDate(Date date);

    Map<String,Integer> airportSurrKeyByICAOCode();

}
