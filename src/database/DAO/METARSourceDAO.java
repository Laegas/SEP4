package database.DAO;

import model.weather.Airport;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;

public interface METARSourceDAO {
    void insertWeatherRecord(WeatherRecord record);
    void insertAirport(Airport airport);

}
