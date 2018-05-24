package model;

import model.igc.DataPoint;
import model.weather.WeatherRecord;

/**
 * Created by kenneth on 24/05/2018.
 */
public class IGCJoinWeather implements Comparable<IGCJoinWeather>{
    private DataPoint dataPoint;
    private WeatherRecord weatherRecord;

    public IGCJoinWeather(DataPoint dataPoint, WeatherRecord weatherRecord) {
        this.dataPoint = dataPoint;
        this.weatherRecord = weatherRecord;
    }

    public DataPoint getDataPoint() {
        return dataPoint;
    }

    public WeatherRecord getWeatherRecord() {
        return weatherRecord;
    }

    @Override
    public int compareTo(IGCJoinWeather o) {
        return dataPoint.compareTo(o.getDataPoint());
    }

    /**
     * not a full equals method only with what i need #Kenneth
     * @param object
     * @return
     */
    public boolean equals(Object object) {
        if (!(object instanceof IGCJoinWeather))
            return false;

        IGCJoinWeather other = (IGCJoinWeather) object;
        return this.dataPoint.equals(other.getDataPoint());
    }
}
