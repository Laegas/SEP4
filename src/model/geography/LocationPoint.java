package model.geography;

/**
 * Created by kenneth on 22/05/2018.
 */
public class LocationPoint {
    private Latitude latitude;
    private Longitude longitude;

    public LocationPoint(Latitude latitude, Longitude longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }
}
