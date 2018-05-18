package database.DAO;

import model.igc.DataLogger;
import model.weather.WeatherRecord;

public interface IGCDataDAO {
    void insertDataLogger(DataLogger logger);
    void insertWeatherRecord(WeatherRecord record);
}
