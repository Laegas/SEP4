package config;

import model.outputData.FeatureProperties;
import org.json.JSONException;

public class VisualizationConfig {

    // ********************* config for area *******************************************
    public static final double LONGITUDE_START = 8;         // 8°
    public static final double LATITUDE_START = 54.5;       // 54°30'
    public static final double LONGITUDE_END = 11;          // 11°
    public static final double LATITUDE_END = 57.5;         // 57°30'

    // ********************* config for size of individual field ***********************
    public static final double SOUTH_TO_NORTH_ARC = 0.25;    // 0°0'15''
    public static final double WEST_TO_EAST_ARC = 0.5;     // 0°0'30''

    // ********************* config for grid size **************************************
    public static final int WIDTH = (int)((LONGITUDE_END - LONGITUDE_START) * 60 / WEST_TO_EAST_ARC);   // 720
    public static final int HEIGHT = (int)((LATITUDE_END - LATITUDE_START) * 60 / SOUTH_TO_NORTH_ARC);  // 360

    // ********************* config for resource (JSON) generation *********************
    public static final int CHUNKS = 1;

    /**
     * Used for evaluating if a feature should be added to visualization.
     *
     * @return true if feature properties are meaningful to show in visualization.
     */
    public static boolean IS_MEANINGFUL(FeatureProperties grid) {
        return grid.getTotal().getNumberOfRegisteredFlights()!=0;
        /*return grid.getTotal().getNumberOfRegisteredFlights()>8 &&
                ((double)grid.getTotal().getNumberOfRegisteredThermal() /
                 (double)grid.getTotal().getNumberOfRegisteredFlights()*100)>30.0;*/
    }

    // naming convention for properties: f = flights; t = total; l@ = layer number
    public static final String[] PROPERTIES = new String[]{"f", "t", "l1f", "l1t", "l2f", "l2t", "l3f", "l3t"};
    public static String[] PROPERTY_VALUES(FeatureProperties feature) throws JSONException {
        String[] values = new String[PROPERTIES.length];
        values[0] = feature.getTotal().getNumberOfRegisteredFlights() + "";
        values[1] = feature.getTotal().getNumberOfRegisteredThermal() + "";
        values[2] = feature.getBetweenZeroAndTenDegree().getNumberOfRegisteredFlights() + "";
        values[3] = feature.getBetweenZeroAndTenDegree().getNumberOfRegisteredThermal() + "";
        values[4] = feature.getBetweenTenAndTwentyDegree().getNumberOfRegisteredFlights() + "";
        values[5] = feature.getBetweenTenAndTwentyDegree().getNumberOfRegisteredThermal() + "";
        values[6] = feature.getBetweenTwentyAndThirtyDegree().getNumberOfRegisteredFlights() + "";
        values[7] = feature.getBetweenTwentyAndThirtyDegree().getNumberOfRegisteredThermal() + "";
        for(String value : values)
            if(value == null || value.equals(""))
                throw new JSONException("Not all properties are initialized");
        return values;
    }

    // ********************* additional config for Google Maps *************************
    // level of zoom for Google Maps. 7 for centered Denmark shows whole Denmark.
    public static final int ZOOM = 7;
    // sets minimum opacity for alpha channel of rgba
    public static final double MINIMUM_ALPHA = 0.3;
    // property on which opacity is based
    public static final String ALPHA_PROPERTY = PROPERTIES[1];
    public static final String VISIBILITY_FACTOR = PROPERTIES[1] + "/" + PROPERTIES[0];
    public static final String[] DISPLAY_STRING_FORMAT = new String[] // @1 through to @9 represents PROPERTIES
            {"T:@2 / F:@1",
             "L1T:@4 / L1F:@3",
             "L2T:@6 / L2F:@5",
             "L3T:@8 / L3F:@7",};

}
