package visualization;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class PrintToExcelSXSSF {
    public static void main(String[] args) {
        SXSSFWorkbook wb = new SXSSFWorkbook(10); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();

        Row row = sh.createRow(0);
        for(int cellnum = 0; cellnum < 1000; cellnum++){
            Cell cell = row.createCell(cellnum);
            String address = new CellReference(cell).formatAsString();
            cell.setCellValue(address);
        }

        for(int rownum = 1; rownum < 1000; rownum++){
            row = sh.createRow(rownum);
            for(int cellnum = 0; cellnum < 1000; cellnum++){
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }
        }

        try {
            FileOutputStream out = new FileOutputStream("sxssf.xlsx");
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
