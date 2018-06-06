package config;

import model.outputData.FeatureProperties;
import model.outputData.OutputData;
import org.json.JSONException;
import visualization.javaFxMaps.Color;
import visualization.javaFxMaps.ColorProperty;
import visualization.javaFxMaps.LayerWrapper;
import visualization.javaFxMaps.Property;

public class VisualizationConfig {

    // ********************* config for area *******************************************
    public static final double LONGITUDE_START = 8;         // 8°
    public static final double LATITUDE_START = 54.5;       // 54°30'
    public static final double LONGITUDE_END = 11;          // 11°
    public static final double LATITUDE_END = 57.5;         // 57°30'

    // ********************* config for size of individual feature *********************
    public static final double SOUTH_TO_NORTH_ARC = 0.25;    // 0°0'15''
    public static final double WEST_TO_EAST_ARC = 0.5;     // 0°0'30''

    // ********************* config for grid size **************************************
    public static final int WIDTH = (int)((LONGITUDE_END - LONGITUDE_START) * 60 / WEST_TO_EAST_ARC);   // 360
    public static final int HEIGHT = (int)((LATITUDE_END - LATITUDE_START) * 60 / SOUTH_TO_NORTH_ARC);  // 720

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

    // naming convention for properties: f = flights; t = total; wd = wind direction; l@ = layer number
    public static final Property[] PROPERTIES = new Property[]{new Property("f"), new Property("t"), new Property
            ("wd45f"), new Property("wd45t"), new Property("wd135f"), new Property("wd135t"), new Property
            ("wd225f"), new Property("wd225t"), new Property("wd315f"), new Property("wd315t")};

    public static String[] PROPERTY_VALUES(FeatureProperties feature) throws JSONException {
        String[] values = new String[PROPERTIES.length];
        values[0] = feature.getTotal().getNumberOfRegisteredFlights() + "";
        values[1] = feature.getTotal().getNumberOfRegisteredThermal() + "";
        values[2] = feature.getWindDirZeroToNinety().getNumberOfRegisteredFlights() + "";
        values[3] = feature.getWindDirZeroToNinety().getNumberOfRegisteredThermal() + "";
        values[4] = feature.getWindDirNinetyToHundredEighty().getNumberOfRegisteredFlights() + "";
        values[5] = feature.getWindDirNinetyToHundredEighty().getNumberOfRegisteredThermal() + "";
        values[6] = feature.getWindDirHundredEightyToTwoHundredSeventy().getNumberOfRegisteredFlights() + "";
        values[7] = feature.getWindDirHundredEightyToTwoHundredSeventy().getNumberOfRegisteredThermal() + "";
        values[8] = feature.getWindDirTwoHundredSeventyToThreeHundredSixty().getNumberOfRegisteredFlights() + "";
        values[9] = feature.getWindDirTwoHundredSeventyToThreeHundredSixty().getNumberOfRegisteredThermal() + "";
        for(String value : values)
            if(value == null || value.equals(""))
                throw new JSONException("Not all properties are initialized");
        return values;
    }

    // ********************* additional global config for Google Maps ******************
    // level of zoom for Google Maps. 7 for centered Denmark shows whole Denmark.
    public static final int ZOOM = 7;

    // ********************* additional layer config for Google Maps *******************
    public static String[] LAYER_NAMES = new String[]{"Flights heat map", "Thermals", "Wind direction 0-90", "Wind " +
            "direction 90-180", "Wind direction 180-270", "Wind direction 270-360"};
    public static LayerWrapper[] LAYERS(OutputData grid) {
        return new LayerWrapper[]{
                new LayerWrapper(LAYER_NAMES[0], 0.1, PROPERTIES[0], grid.getMaxTotalRegisteredFlightCount(),
                        "@1/" + grid.getMaxTotalRegisteredFlightCount(), "F:@1",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0)),
                new LayerWrapper(LAYER_NAMES[1], 0.1, PROPERTIES[1], grid.getMaxTotalRegisteredFlightCount(),
                        "@1/@0", "T:@1 / F:@0",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(0, 255, 0)),
                new LayerWrapper(LAYER_NAMES[2], 0.3, PROPERTIES[3], 5,
                        "@3/@2", "T:@3 / F:@2",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0)),
                new LayerWrapper(LAYER_NAMES[3], 0.3, PROPERTIES[5], 5,
                        "@5/@4", "T:@5 / F:@4",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0)),
                new LayerWrapper(LAYER_NAMES[4], 0.3, PROPERTIES[7], 5,
                        "@7/@6", "T:@7 / F:@6",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0)),
                new LayerWrapper(LAYER_NAMES[5], 0.3, PROPERTIES[9], 5,
                        "@9/@8", "T:@9 / F:@8",
                        ColorProperty.MAXCOLOR, ColorProperty.MAXCOLOR, new Color(255, 0, 0))
        };
    }
}
