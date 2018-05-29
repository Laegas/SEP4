package model.weather;

public class Altitude {
    private int altitude;


    Altitude(int altitude)
    {
        setAltitude(altitude);
    }
    public void setAltitude(int altitude) {
        this.altitude = altitude;
    }

    public int getAltitude() {
        return altitude;
    }
}
