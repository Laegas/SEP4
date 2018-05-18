package visualization;

import model.geography.Latitude;
import model.geography.Longitude;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static config.VisualizationConfig.*;

/**
 * Created by kryst on 5/16/2018.
 */
public class PrintToTxt {

    private static PrintToTxt instance;

    private PrintToTxt() {}

    public static PrintToTxt getInstance() {
        if(instance == null) {
            instance = new PrintToTxt();
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
                String latitude = Latitude.convertToLatitude(i);
                String longitude = Longitude.convertToLongitude(j);
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
}