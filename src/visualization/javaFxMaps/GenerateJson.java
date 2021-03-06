package visualization.javaFxMaps;


import model.geography.Latitude;
import model.geography.Longitude;
import model.outputData.FeatureProperties;
import model.outputData.OutputData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static config.VisualizationConfig.*;

public class GenerateJson {
    
    private static GenerateJson instance;
    
    private GenerateJson() {}
    
    public static GenerateJson getInstance() {
        if(instance == null)
            instance = new GenerateJson();
        return instance;
    }
    
    public int generateJson(OutputData data) throws JSONException {
        FeatureProperties[][] grid = data.getFeatureProperties();

        DecimalFormat df = new DecimalFormat("#.######", new DecimalFormatSymbols(Locale.US));
        List<String> lines = new ArrayList<>();
        lines.add("var json = [");
        FeatureProperties[][] chunkGrid = new FeatureProperties[HEIGHT / CHUNKS][WIDTH / CHUNKS];
        int featureCounter = 0;

        for (int chunkColumn = 0; chunkColumn < CHUNKS; chunkColumn++) {
            for (int chunkRow = 0; chunkRow < CHUNKS; chunkRow++) {
                for (int i = 0; i < chunkGrid.length; i++) {
                    for (int j = 0; j < chunkGrid[0].length; j++) {
                        chunkGrid[i][j] = grid[i + chunkColumn * HEIGHT / CHUNKS][j + chunkRow * WIDTH / CHUNKS];
                    }
                }

                JSONArray features = new JSONArray();

                for (int i = 0; i < chunkGrid.length; i++) {
                    for (int j = 0; j < chunkGrid[0].length; j++) {
                        if (IS_MEANINGFUL(chunkGrid[i][j])) {
                            featureCounter++;
                            String[] propertyValues = PROPERTY_VALUES(chunkGrid[i][j]);
                            JSONObject properties = new JSONObject();
                            for (int propertyIndex = 0; propertyIndex < PROPERTIES.length; propertyIndex++) {
                                properties.put(PROPERTIES[propertyIndex].toString(), propertyValues[propertyIndex]);
                            }
                            features.put(new JSONObject().put("type", "Feature").put("properties", properties).put("geometry", new JSONObject().put("type", "Polygon").put("coordinates", new JSONArray().put(new JSONArray().put(new JSONArray().put(Double.parseDouble(df.format(Longitude.convertToLongitudeDouble(j + WIDTH / CHUNKS * chunkRow)))).put(Double.parseDouble(df.format(Latitude.convertToLatitudeDouble(i + HEIGHT / CHUNKS * chunkColumn))))).put(new JSONArray().put(Double.parseDouble(df.format(Longitude.convertToLongitudeDouble(j + 1 + WIDTH / CHUNKS * chunkRow)))).put(Double.parseDouble(df.format(Latitude.convertToLatitudeDouble(i + HEIGHT / CHUNKS * chunkColumn))))).put(new JSONArray().put(Double.parseDouble(df.format(Longitude.convertToLongitudeDouble(j + 1 + WIDTH / CHUNKS * chunkRow)))).put(Double.parseDouble(df.format(Latitude.convertToLatitudeDouble(i + 1 + HEIGHT / CHUNKS * chunkColumn))))).put(new JSONArray().put(Double.parseDouble(df.format(Longitude.convertToLongitudeDouble(j + WIDTH / CHUNKS * chunkRow)))).put(Double.parseDouble(df.format(Latitude.convertToLatitudeDouble(i + 1 + HEIGHT / CHUNKS * chunkColumn))))).put(new JSONArray().put(Double.parseDouble(df.format(Longitude.convertToLongitudeDouble(j + WIDTH / CHUNKS * chunkRow)))).put(Double.parseDouble(df.format(Latitude.convertToLatitudeDouble(i + HEIGHT / CHUNKS * chunkColumn)))))))));

                        }
                    }
                }

                JSONObject json = new JSONObject().put("type", "FeatureCollection").put("features", features);

                lines.add(json.toString() + ((chunkColumn == CHUNKS - 1 && chunkRow == CHUNKS - 1)? "" : ","));
            }
        }
        lines.add("];");

        try {
            Path file = Paths.get("src/visualization/javaFxMaps/json.js");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return featureCounter;
    }
}
