package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class DegreeCelcius {
    private double temperature;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public DegreeCelcius(double temperature) {

        this.temperature = temperature;
    }
}
