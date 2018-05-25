package database.DAO;

import model.time.Date;
import model.weather.WeatherRecord;

import java.util.List;

/**
 * Created by kenneth on 24/05/2018.
 */
public interface WeatherDimensionalDao {
    public List<WeatherRecord> getWeatherRecord(Date date);
}
