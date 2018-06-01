package config;

import model.outputData.FeatureProperties;
import model.outputData.OutputData;
import org.json.JSONException;
import visualization.javaFxMaps.Color;
import visualization.javaFxMaps.ColorProperty;
import visualization.javaFxMaps.LayerWrapper;

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
    public static final String[] PROPERTIES = new String[]{"f", "t"};
    public static String[] PROPERTY_VALUES(FeatureProperties feature) throws JSONException {
        String[] values = new String[PROPERTIES.length];
        values[0] = feature.getTotal().getNumberOfRegisteredFlights() + "";
        values[1] = feature.getTotal().getNumberOfRegisteredThermal() + "";
        for(String value : values)
            if(value == null || value.equals(""))
                throw new JSONException("Not all properties are initialized");
        return values;
    }

    // ********************* additional global config for Google Maps ******************
    // level of zoom for Google Maps. 7 for centered Denmark shows whole Denmark.
    public static final int ZOOM = 7;

    // ********************* additional layer config for Google Maps *******************
    public static String[] LAYER_NAMES = new String[]{"All", "All2"};
    public static LayerWrapper[] LAYERS(OutputData grid) {
        return new LayerWrapper[]{
                new LayerWrapper(LAYER_NAMES[0], 0.3, PROPERTIES[1], grid.getMaxTotalRegisteredFlightCount(),
                        PROPERTIES[1] + "/" + PROPERTIES[0], "T:@2 / F:@1",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0)),
                new LayerWrapper(LAYER_NAMES[1], 0.3, PROPERTIES[1], grid.getMaxTotalRegisteredFlightCount(),
                        PROPERTIES[1] + "/" + PROPERTIES[0], "T:@2 / F:@1",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0))
        };
    }
}
