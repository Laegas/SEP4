package database.DAO;

import model.weather.WeatherRecord;

public interface METARSourceDAO {
    void insertWeatherRecord(WeatherRecord record);

}
