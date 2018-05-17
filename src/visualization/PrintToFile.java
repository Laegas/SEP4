package visualization;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by kryst on 5/16/2018.
 */
public class PrintToFile {

    public static final double LONGITUDE_START = 8;         // 8°
    public static final double LATITUDE_START = 54.5;       // 54°30'
    public static final double LONGITUDE_END = 11;          // 11°
    public static final double LATITUDE_END = 57.5;         // 57°30'
    public static final double SOUTH_TO_NORTH_ARC = 0.5;    // 0°0'30''
    public static final double WEST_TO_EAST_ARC = 0.25;     // 0°0'15''
    public static final int WIDTH = (int)((LONGITUDE_END - LONGITUDE_START) * 60 / WEST_TO_EAST_ARC);   // 720
    public static final int HEIGHT = (int)((LATITUDE_END - LATITUDE_START) * 60 / SOUTH_TO_NORTH_ARC);  // 360

    public static void main(String[] args) {

        List<String> lines = new ArrayList<>();
        StringBuilder line = new StringBuilder();

        int[][] grid = new int[HEIGHT][WIDTH];

        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                grid[i][j] = (int)Math.floor(Math.random() * 10);
            }
        }

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
            Path file = Paths.get("output.txt");
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static String convertToLongitude(int x) {
        if(x > WIDTH || x < 0)
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