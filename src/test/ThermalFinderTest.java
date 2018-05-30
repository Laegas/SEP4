package test;

import config.FileConfig;
import model.geography.Latitude;
import model.geography.Longitude;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.Glider;
import model.igc.ThermalDataPointGroup;
import model.time.Date;
import model.time.Time;
import org.junit.Test;
import util.file.FileDecoder;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class ThermalFinderTest {

    @Test
        public void testGPSThermalFinder()
    {
        FileDecoder fd = new FileDecoder(FileConfig.TEST_PATH+"/testIGCThermalFinder.igc");
        Flight flight = fd.readIGCFile();
        ThermalFinder finder = new ThermalFinderImp();
        List<ThermalDataPointGroup> group =finder.findThermalsUsingGPSAltitude(flight);

        assertEquals(35, group.size());//how many thermals there are
        assertTrue("If first altitude is higher than last then true.",group.get(0).getGroup().get(0).getGPSAltitude()<group.get(0).getGroup().get(group.get(0).getGroup().size()-1).getGPSAltitude());

    }


    @Test
        public void testPressureThermalFinder()
    {
        FileDecoder fd = new FileDecoder(FileConfig.TEST_PATH+"/testIGCThermalFinder.igc");
        Flight flight = fd.readIGCFile();
        ThermalFinder finder = new ThermalFinderImp();
        List<ThermalDataPointGroup> group =finder.findThermalsUsingPressureAltitude(flight);
        assertEquals(35, group.size());//how many thermals there are
        assertTrue("If first altitude is higher than last then true.",group.get(0).getGroup().get(0).getPressureAltitude()<group.get(0).getGroup().get(group.get(0).getGroup().size()-1).getPressureAltitude());
    }

    @Test
        public void testThermalFinderWithForgedData()
    {
        List<DataPoint> points = new ArrayList<>();
        for(int i = 0;i<60;i++)
        {
            throw new RuntimeException("uncomment an fix 'I changed the constructor' - kenneth");
//            points.add(new DataPoint(new Time(1,1,i),new Longitude(1,1,1),//only the time and altitudes matters here
//                    new Latitude(1,1,1),'A', 10+(i*4), 10+(i*6), i, i));
        }
        Flight flight = new Flight(new Date(1,1,1),points,new Glider(101,"UNIQUE_NO"),1);
        ThermalFinder finder = new ThermalFinderImp();
        List<ThermalDataPointGroup> group = finder.findThermalsUsingGPSAltitude(flight);
        assertEquals(1,group.size());//expect 1 because altitude gain between each data point is more than 5
        group = finder.findThermalsUsingPressureAltitude(flight);
        assertEquals(0, group.size());//expect 0 because altitude gain between each data point is less than 5
        points.clear();//clears list


        for(int i = 0;i<60;i++)
        {
            throw new RuntimeException("uncomment an fix 'I changed the constructor' - kenneth");
//            points.add(new DataPoint(new Time(1,1,i),new Longitude(1,1,1),//only the time and altitudes matters here
//                    new Latitude(1,1,1),'A', 10-(i*4), 10-(i*6), i, i));
        }
        flight = new Flight(new Date(1,1,1),points,new Glider(101,"UNIQUE_NO"),1);
        group = finder.findThermalsUsingPressureAltitude(flight);

        assertEquals(0,group.size());//expect 0 because there is a continuously decline in altitude;
        group = finder.findThermalsUsingGPSAltitude(flight);
        assertEquals(0,group.size());
        points.clear(); //clears list

       int count = 0;


        for(int i = 0;i<5;i++)
        {
            for(int j = 0;j<50;j++)
            {
                throw new RuntimeException("uncomment an fix 'I changed the constructor' - kenneth");
//                points.add(new DataPoint(new Time(count++),new Longitude(1,1,1),//only the time and altitudes matters here
//                        new Latitude(1,1,1),'A', i+(count*6), i+(count*6), i, i));
            }
            for(int j =0;j<30;j++)
            {
                throw new RuntimeException("uncomment an fix 'I changed the constructor' - kenneth");
//                points.add(new DataPoint(new Time(count++),new Longitude(1,1,1),//only the time and altitudes matters here
//                        new Latitude(1,1,1),'A', count, count, i, i));
            }
        }
        flight = new Flight(new Date(1,1,1),points,new Glider(101,"UNIQUE_NO"),1);
        group = finder.findThermalsUsingGPSAltitude(flight);

        assertEquals(5,group.size());//except 5 thermals because there is 5 iterations of thermal creation.


    }

}