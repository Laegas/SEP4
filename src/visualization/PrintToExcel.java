package visualization;



import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;

public class PrintToExcel {

    public static void main(String[] args) {
        try {
            // create a new file
            FileOutputStream out = new FileOutputStream("workbook.xls");
            // create a new workbook
            Workbook wb = new HSSFWorkbook();
            // create a new sheet
            Sheet s = wb.createSheet();
            // declare a row object reference
            Row r = null;
            // declare a cell object reference
            Cell c = null;
            // create 2 cell styles
            CellStyle headingCs = wb.createCellStyle();
            CellStyle dataCs = wb.createCellStyle();
            DataFormat df = wb.createDataFormat();
            // create font
            Font f = wb.createFont();

            //set font 1 to 12 point type
            f.setFontHeightInPoints((short) 10);
            //make it black
            f.setColor(Font.COLOR_NORMAL);

            //set cell style
            headingCs.setFont(f);
            //set the cell format
            //headingCs.setDataFormat(df.getFormat("#,##0.0")); todo

            //set a thin border
            //headingCs.setBorderBottom(cs2.BORDER_THIN); todo
            //fill w fg fill color
            //headingCs.setFillBackgroundColor();
            //headingCs.setFillPattern((short) CellStyle.SOLID_FOREGROUND); todo
            //set the cell format to text see DataFormat for a full list
            headingCs.setDataFormat(HSSFDataFormat.getBuiltinFormat("text"));

            // set the font
            dataCs.setFont(f);

            // set the sheet name in plain ascii
            wb.setSheetName(0, "HSSF Test");

            // create a sheet with 30 rows (0-29)
            int rownum;
            for (rownum = 0; rownum < 30; rownum++) {
                // create a row
                r = s.createRow(rownum);
                // on every other row
                /*
                if ((rownum % 2) == 0) {
                    // make the row height bigger  (in twips - 1/20 of a point)
                    r.setHeight((short) 0x249);
                }
                */

                // create 10 cells (0-9) (the += 2 becomes apparent later
                for (short cellnum = (short) 0; cellnum < 10; cellnum += 2) {
                    // create a numeric cell
                    c = r.createCell(cellnum);
                    // do some goofy math to demonstrate decimals
                    c.setCellValue(rownum * 10000 + cellnum + (((double) rownum / 1000) + ((double) cellnum / 10000)));

                    String cellValue;

                    // create a string cell (see why += 2 in the
                    c = r.createCell((short) (cellnum + 1));

                    // on every other row
                    if ((rownum % 2) == 0) {
                        // set this cell to the first cell style we defined
                        c.setCellStyle(dataCs);
                        // set the cell's string value to "Test"
                        c.setCellValue("Test");
                    } else {
                        c.setCellStyle(headingCs);
                        c.setCellValue("Test2");
                    }


                    // make this column a bit wider
                    s.setColumnWidth((short) (cellnum + 1), (short) ((50 * 8) / ((double) 1 / 20)));
                }
            }

            r = s.createRow(rownum);


            // write the workbook to the output stream
            // close our file (don't blow out our file handles
            wb.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}