package test;

import config.FileConfig;
import model.igc.DataPoint;
import model.igc.Flight;
import model.weather.WeatherRecord;
import org.junit.Test;
import util.file.FileDecoder;

import javax.xml.crypto.Data;

import static org.junit.Assert.*;

public class FileDecoderTest {
@Test
    public void readIGCTestFileWithExpectedValues()
{
    FileDecoder fd = new FileDecoder(FileConfig.TEST_PATH+"/testIGCexpected.igc");
    Flight flight = fd.readIGCFile();
    DataPoint point1 = flight.getDatalog().get(0);
    DataPoint point2 = flight.getDatalog().get(1);
    DataPoint lastPoint = flight.getDatalog().get(flight.getDatalog().size()-1);
    assertEquals(point1.getPressureAltitude(),-15);
    assertEquals(point1.getGPSAltitude(),33);;
    assertEquals(point2.getGPSAltitude(),32);
    assertEquals(lastPoint.getTime().getSecond().getSecond(),26);
}

@Test
    public void readIGCTestFileWithCorruptedValues()
    {
    FileDecoder fd = new FileDecoder(FileConfig.TEST_PATH+"/testIGCCorrupted.igc");
     try{
      fd.readIGCFile();
      fail("Expected a NullPointerException.");
          }
        catch(Exception e)
      {
          assertTrue("Expect Number Format Exception",e.getClass()==NumberFormatException.class);
      }

    }
    @Test
    public void readMETARTestFileWithExpectedValues()
    {
        FileDecoder fd = new FileDecoder(FileConfig.TEST_PATH+"/testMETARExpected.txt");
        WeatherRecord[] recordArray = fd.readMETARFile();
        assertEquals(13,recordArray.length);
        assertEquals(2018,recordArray[0].getYear().getYear());
        assertEquals(5,recordArray[0].getMonth().getMonthNumber());
        assertEquals(360,recordArray[0].getVaryingWindDirection().getFrom().getDegree());
        assertEquals(60,recordArray[0].getVaryingWindDirection().getTo().getDegree());
        assertEquals(10,recordArray[1].getVaryingWindDirection().getFrom().getDegree());
        assertEquals(70, recordArray[1].getVaryingWindDirection().getTo().getDegree());
        assertEquals(9 , recordArray[0].getDewPoint().getTemperature(),0);

        try{
            assertEquals(4,recordArray[0].getWind().getWindSpeed().getKnots());
            assertEquals(30,recordArray[0].getWind().getWindDirection().getDegree().getDegree());
        }
        catch(Exception e)
        {
            fail("Exception is not supposed to be thrown");
            e.printStackTrace();
        }
    }
    @Test
    public void readMETARTestFileWithCorruptedValues()
    {
        FileDecoder fd = new FileDecoder(FileConfig.TEST_PATH+"/testMETARCorrupted.txt");
        WeatherRecord[] recordArray = fd.readMETARFile();
        assertEquals(0,recordArray.length);
//        nothing is supposed to happen other than a METAR Exception was caught in the FileDecoder method decode readMETARFile()
//        no lines have been successfully decoded.
    }


}