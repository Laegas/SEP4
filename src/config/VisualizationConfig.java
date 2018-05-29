package config;

import model.outputData.FeatureProperties;
import model.outputData.OutputData;

public class VisualizationConfig {

    // config for area
    public static final double LONGITUDE_START = 8;         // 8°
    public static final double LATITUDE_START = 54.5;       // 54°30'
    public static final double LONGITUDE_END = 11;          // 11°
    public static final double LATITUDE_END = 57.5;         // 57°30'

    // config for size of individual field
    public static final double SOUTH_TO_NORTH_ARC = 0.25;    // 0°0'15''
    public static final double WEST_TO_EAST_ARC = 0.5;     // 0°0'30''

    // config for grid size
    public static final int WIDTH = (int)((LONGITUDE_END - LONGITUDE_START) * 60 / WEST_TO_EAST_ARC);   // 720
    public static final int HEIGHT = (int)((LATITUDE_END - LATITUDE_START) * 60 / SOUTH_TO_NORTH_ARC);  // 360

    // config for resource (JSON) generation
    public static final int CHUNKS = 1;
    public static final String[] PROPERTIES = new String[]{"flights", "thermals", "test"};
    public static String[] PROPERTY_VALUES(FeatureProperties feature) {
        String[] values = new String[PROPERTIES.length];
        values[0] = feature.getTotal().getNumberOfRegisteredFlights() + "";
        values[1] = feature.getTotal().getNumberOfRegisteredThermal() + "";
        values[2] = "1";
        return values;
    }

    // additional config for Google Maps
    public static final int ZOOM = 7;
    public static final String ALPHA_PROPERTY = PROPERTIES[0];
    public static final String[] DISPLAY_PROPERTIES = new String[]{PROPERTIES[1], PROPERTIES[0]};
    public static final double MINIMUM_ALPHA = 0.3;
    public static final String DISPLAY_STRING_FORMAT = "T:@1 / F:@2"; // @1 through to @9 represents DISPLAY_PROPERTIES


}
