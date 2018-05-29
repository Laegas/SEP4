package util.geography;

import model.geography.Latitude;
import model.geography.Longitude;
import org.geotools.referencing.GeodeticCalculator;

import javax.measure.Measure;
import javax.measure.quantity.Length;
import javax.measure.unit.SI;

/**
 * Created by kenneth on 29/05/2018.
 */
public class GeoCaluclator {
    private static GeodeticCalculator geoCalc = new GeodeticCalculator();

    public static double getGeoDistance(Latitude lat1, Longitude long1, Latitude lat2, Longitude long2) {

        geoCalc.setStartingGeographicPoint(long1.getSignedDegreeFormat(), lat1.getSignedDegreeFormat());
        geoCalc.setDestinationGeographicPoint(long2.getSecond(), lat2.getSignedDegreeFormat());

        double distance = geoCalc.getOrthodromicDistance();
        double bearing = geoCalc.getAzimuth();

        Measure<Double,Length> dist = Measure.valueOf(distance, SI.METER);

        return dist.doubleValue(SI.METER);
    }
}
