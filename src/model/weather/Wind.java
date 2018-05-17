package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class Wind {
    WindDirection windDirection;
    WindSpeed windSpeed;

    public WindDirection getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(WindDirection windDirection) {
        this.windDirection = windDirection;
    }

    public WindSpeed getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(WindSpeed windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Wind(WindDirection windDirection, WindSpeed windSpeed) {

        this.windDirection = windDirection;
        this.windSpeed = windSpeed;
    }
}
