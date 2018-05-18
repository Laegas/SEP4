package visualization;

import model.geography.Latitude;
import model.geography.Longitude;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

import static config.VisualizationConfig.*;

public class PrintToExcelSXSSF {
    private static PrintToExcelSXSSF instance;

    private PrintToExcelSXSSF() {}

    public static PrintToExcelSXSSF getInstance() {
        if(instance == null) {
            instance = new PrintToExcelSXSSF();
        }
        return instance;
    }

    public void printGridToXLSX(int[][] grid) throws InvalidGridDimensionException {
        printGridToXLSX(grid, "output");
    }

    public void printGridToXLSX(int[][] grid, String filename) throws InvalidGridDimensionException {

        if(grid.length != HEIGHT || grid[0].length != WIDTH)
            throw new InvalidGridDimensionException("Required dimensions: [" + HEIGHT + "][" + WIDTH + "]. Was [" +
                    grid.length + "][" + grid[0].length + "]");

        SXSSFWorkbook wb = new SXSSFWorkbook(10); // keep 10 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();

        Row row = sh.createRow(0);
        for(int cellnum = 0; cellnum < WIDTH; cellnum++){
            Cell cell = row.createCell(cellnum+1);
            cell.setCellValue(Longitude.convertToLongitude(cellnum));
        }

        for(int rownum = 1; rownum < HEIGHT + 1; rownum++){
            row = sh.createRow(rownum);
            Cell cell = row.createCell(0);
            cell.setCellValue(Latitude.convertToLatitude(rownum-1));
            for(int cellnum = 0; cellnum < WIDTH; cellnum++){
                cell = row.createCell(cellnum + 1);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(grid[rownum-1][cellnum]);
            }
        }


        try {
            FileOutputStream out = new FileOutputStream(filename + ".xlsx");
            wb.write(out);
            out.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}
