package model.geography;

import javax.xml.stream.Location;

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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LocationPoint)) {
            return false;
        }

        LocationPoint other = (LocationPoint) obj;

        return (this.latitude.equals(other.getLatitude()) &&
                this.longitude.equals(other.getLongitude()));
    }
}
