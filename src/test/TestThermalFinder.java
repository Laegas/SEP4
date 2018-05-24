package test;

import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import util.file.FileDecoder;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;

import java.util.ArrayList;
import java.util.List;

public class TestThermalFinder {
    public static void main(String[] args)
    {
        FileDecoder fd = new FileDecoder("C:\\igc.\\85C_2B.igc");
        Flight flight = fd.readIGCFile();
        System.out.println(flight.getDatalog().size());

        ThermalFinder finder = new ThermalFinderImp();
        List<ThermalDataPointGroup> groups = finder.findThermalsUsingPressureAltitude(flight);

        System.out.println(groups.size() +" amount of thermals");
        for(int i = 0;i<groups.size();i++)
        {
            System.out.println(  groups.get(i).getGroup().size()+" amount of datapoints in thermal");
            ThermalDataPointGroup a = groups.get(i);
            System.out.println(a.getGroup().get(0).getPressureAltitude()+"   to    "+ a.getGroup().get(a.getGroup().size()-1).getPressureAltitude());
        }


    }
}
