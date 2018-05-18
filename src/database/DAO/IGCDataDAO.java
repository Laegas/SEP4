package database.DAO;

import model.igc.Flight;
import model.weather.WeatherRecord;

public interface IGCDataDAO {
    void insertDataLogger(Flight logger);
    void insertWeatherRecord(WeatherRecord record);
}
