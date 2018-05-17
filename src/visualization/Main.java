package visualization;

import static visualization.VisualizationConfig.*;

public class Main {
    public static void main(String[] args) {

        int[][] grid = new int[HEIGHT][WIDTH];

        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                grid[i][j] = (int)Math.floor(Math.random() * 10);
            }
        }

        PrintToFile p = PrintToFile.getInstance();
        try {
            p.printGridToTxt(grid);
        } catch (InvalidGridDimensionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
