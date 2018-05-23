package launchable;

import model.outputData.OutputData;
import visualization.javaFxMaps.GenerateJSSettings;
import visualization.javaFxMaps.GenerateJson;
import visualization.InvalidGridDimensionException;

import static config.VisualizationConfig.HEIGHT;
import static config.VisualizationConfig.WIDTH;

public class GenerateGMapsResources {
    public static void main(String[] args) {

        GenerateJSSettings.getInstance().generateSettings();

        OutputData grid = new OutputData();

        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                for(int k = 0; k < ((Math.random() > 0.9) ? (int) (Math.floor(Math.random() * 10) + 1) : 0); k++) {
                    grid.registerFlightAtIndex(i, j);
                    grid.registerThermalAtIndex(i, j);
                }
            }
        }



        try {
            GenerateJson.getInstance().generateJson(grid);
        } catch (InvalidGridDimensionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
