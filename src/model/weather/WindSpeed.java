package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindSpeed {
    private double windSpeed;

    public WindSpeed(double windSpeed) {
        setWindSpeed(windSpeed);
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        if (windSpeed < 0) {
            throw new RuntimeException("We can not have wind speed less than 0");
        }
        this.windSpeed = windSpeed;
    }
}
