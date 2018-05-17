package visualization;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static visualization.VisualizationConfig.*;

/**
 * Created by kryst on 5/16/2018.
 */
public class PrintToFile {

    private static PrintToFile instance;

    private PrintToFile() {}

    public static PrintToFile getInstance() {
        if(instance == null) {
            instance = new PrintToFile();
        }
        return instance;
    }

    public void printGridToTxt(int[][] grid) throws InvalidGridDimensionException {
        printGridToTxt(grid, "output");
    }

    public void printGridToTxt(int[][] grid, String filename) throws InvalidGridDimensionException {

        if(grid.length != HEIGHT || grid[0].length != WIDTH)
            throw new InvalidGridDimensionException("Required dimensions: [" + HEIGHT + "][" + WIDTH + "]. Was [" +
                    grid.length + "][" + grid[0].length + "]");

        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();

        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                String latitude = convertToLatitude(i);
                String longitude = convertToLongitude(j);
                line.append(latitude);
                line.append("N, ");
                line.append(longitude);
                line.append("E");
                for(int k = latitude.length() + longitude.length() + 4; k < 23; k++)
                    line.append(" ");
                line.append("- ");
                line.append(grid[i][j]);
                lines.add(line.toString());
                line.setLength(0);
            }
        }

        try {
            Path file = Paths.get(filename + ".txt");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static String convertToLongitude(int x) {
        if(x > VisualizationConfig.WIDTH || x < 0)
            throw new IllegalArgumentException("Argument has to be from 0 to " + WIDTH);
        int degreeValue = (int)(LONGITUDE_START + x / (WIDTH / (LONGITUDE_END - LONGITUDE_START)));
        int minuteValue = (x / (int)(1 / WEST_TO_EAST_ARC) % 60);
        int secondValue = (x % (int)(1 / WEST_TO_EAST_ARC)) * (60 / (int)(1 / WEST_TO_EAST_ARC));
        return degreeValue + "°" + minuteValue + "'" + secondValue + "''";
    }

    public static String convertToLatitude(int y) {
        if(y > HEIGHT || y < 0)
            throw new IllegalArgumentException("Argument has to be from 0 to " + HEIGHT);
        int degreeValue = (int)(LATITUDE_START + y / (HEIGHT / (LATITUDE_END - LATITUDE_START)));
        int minuteValue = (int)((LATITUDE_START % 1) * 60 + (y / (int)(1 / SOUTH_TO_NORTH_ARC))) % 60;
        int secondValue = (y % (int)(1 / SOUTH_TO_NORTH_ARC)) * (60 / (int)(1 / SOUTH_TO_NORTH_ARC));
        return degreeValue + "°" + minuteValue + "'" + secondValue + "''";
    }
}