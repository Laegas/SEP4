package model.weather;

import model.time.Date;
import model.time.Time;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by kenneth on 24/05/2018.
 */
public class WeatherFactory {
    private static final WeatherFactory INSTANCE = new WeatherFactory();


    private WeatherFactory() {
        // TODO MAKE STUFF WHEN THE WEATHER DAO IS IMPLEMENTED
    }

    public static WeatherFactory getINSTANCE() {
        return INSTANCE;
    }

    public WeatherRecord getWeather(Date date, Time time) {
        throw new NotImplementedException();
    }
}
