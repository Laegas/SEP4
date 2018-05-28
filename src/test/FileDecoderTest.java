package test;

import config.FileConfig;
import model.igc.DataPoint;
import model.igc.Flight;
import org.junit.Test;
import util.file.FileDecoder;

import javax.xml.crypto.Data;

import static org.junit.Assert.*;

public class FileDecoderTest {
@Test
    public void readTestFileWithExpectedValues()
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
    public void readTestFileWithCorruptedValues()
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

}