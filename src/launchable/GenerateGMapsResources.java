package launchable;

import visualization.javaFxMaps.GenerateJSSettings;
import visualization.javaFxMaps.GenerateJson;
import visualization.InvalidGridDimensionException;

import static config.VisualizationConfig.HEIGHT;
import static config.VisualizationConfig.WIDTH;

public class GenerateGMapsResources {
    public static void main(String[] args) {

        GenerateJSSettings.getInstance().generateSettings();

        int[][] grid = new int[HEIGHT][WIDTH];
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                // random data generation
                grid[i][j] = (Math.random() > 0.9) ? (int) (Math.floor(Math.random() * 10) + 1) : 0;
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
