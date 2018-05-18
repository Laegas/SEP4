package launchable;

import visualization.InvalidGridDimensionException;
import visualization.PrintToExcelSXSSF;
import visualization.PrintToTxt;

import static config.VisualizationConfig.*;

public class VisualizationMain {
    public static void main(String[] args) {

        int[][] grid = new int[HEIGHT][WIDTH];

        for(int i = 0; i < HEIGHT; i++) {
            for(int j = 0; j < WIDTH; j++) {
                grid[i][j] = (int)Math.floor(Math.random() * 10);
            }
        }

        // print to txt
        PrintToTxt p = PrintToTxt.getInstance();
        try {
            p.printGridToTxt(grid);
        } catch (InvalidGridDimensionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        // print to excel
        PrintToExcelSXSSF excel = PrintToExcelSXSSF.getInstance();
        try {
            excel.printGridToXLSX(grid);
        } catch (InvalidGridDimensionException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }
}
