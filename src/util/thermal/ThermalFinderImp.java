package util.thermal;

import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import model.outputData.IGCDataGroup;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ThermalFinderImp implements ThermalFinder  {

    private ThermalDataPointGroup findThermalWithGPSAltitude()
    {
        return null;
    }


    @Override
    public List<ThermalDataPointGroup> findThermalsUsingGPSAltitude(Flight flight) {
        List<DataPoint> list = flight.getDatalog();
        Collections.sort(list);
        List<ThermalDataPointGroup> thermalGroups = new ArrayList<>();
        ThermalDataPointGroup group = findThermalWithGPSAltitude();
        while(group!= null)
        {
            thermalGroups.add(group);
            group= findThermalWithGPSAltitude();
        }
        return thermalGroups;
    }

    @Override
    public List<ThermalDataPointGroup> findThermalsUsingPressureAltitude(Flight flight) {
        return null;
    }
}
