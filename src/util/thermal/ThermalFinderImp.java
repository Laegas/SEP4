package util.thermal;

import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ThermalFinderImp implements ThermalFinder  {
    private int thermalStart = -1;
    private int thermalEnd = -1;
    private int scopeSize = 5;
    private double altitudeGainPerSecond;

    public ThermalFinderImp( double altitudeGainedPerSecond)
    {
        this.altitudeGainPerSecond=altitudeGainedPerSecond;
    }

    public ThermalFinderImp() {

    }

    private ThermalDataPointGroup makeGroup(List<DataPoint> list, int start, int end)
    {
        ArrayList<DataPoint> glist = new ArrayList<>();
        for(int i = start;i<end-1;i++)
        {
            glist.add(list.get(i));
        }
        return new ThermalDataPointGroup(glist);
    }

    @Override
    public List<ThermalDataPointGroup> findThermalsUsingGPSAltitude(Flight flight) {
        List<DataPoint> list = flight.getDatalog();
        Collections.sort(list);
        List<ThermalDataPointGroup> thermalGroups = new ArrayList<>();
        int index = 0;
        ArrayList<DataPoint> scope = new ArrayList<>();
        ThermalDataPointGroup group = null;

        while(index<list.size())
        {

            thermalStart=-1;
            thermalEnd=-1;
            boolean thermalStop = false;
            while (!thermalStop&&index<list.size())
            {
                scope.add(list.get(index));
                if(scope.size()>scopeSize)
                    scope.remove(0);
                if(checkScopeUsingGPSAltitude(scope))
                {
                    if(thermalStart==-1)thermalStart=index-scopeSize+1;
                }
                else
                {
                    thermalStop = true;
                }
                index++;
            }
            if(thermalStart!=-1)
            {
                thermalEnd=index;
                group = makeGroup(list, thermalStart, thermalEnd);
                thermalGroups.add(group);
            }
        }

        return thermalGroups;
    }

    private boolean checkScopeUsingGPSAltitude(ArrayList<DataPoint> scope) {

        if(scope.size()!=scopeSize)
            return false;
        int before = scope.get(0).getGPSAltitude();
        for(int i = 1;i<scope.size();i++ )
        {
            if(altitudeGainPerSecond>(scope.get(i).getGPSAltitude()-before))
                return false;
            before = scope.get(i).getGPSAltitude();
        }
        return true;
    }

    @Override
    public List<ThermalDataPointGroup> findThermalsUsingPressureAltitude(Flight flight) {
        List<DataPoint> list = flight.getDatalog();
        Collections.sort(list);
        List<ThermalDataPointGroup> thermalGroups = new ArrayList<>();
        int index = 0;
        ArrayList<DataPoint> scope = new ArrayList<>();
        ThermalDataPointGroup group = null;

        while(index<list.size())
        {

            thermalStart=-1;
            thermalEnd=-1;
            boolean thermalStop = false;
            while (!thermalStop&&index<list.size())
            {
                scope.add(list.get(index));
                if(scope.size()>scopeSize)
                    scope.remove(0);
                if(checkScopeUsingPressureAltitude(scope))
                {
                    if(thermalStart==-1)thermalStart=index-scopeSize+1;
                }
                else
                {
                    thermalStop = true;
                }
                index++;
            }
            if(thermalStart!=-1)
            {
                thermalEnd=index;
                group = makeGroup(list, thermalStart, thermalEnd);
                thermalGroups.add(group);
            }
        }

        return thermalGroups;
    }

    private boolean checkScopeUsingPressureAltitude(ArrayList<DataPoint> scope) {

        if(scope.size()!=scopeSize)
            return false;
    int before = scope.get(0).getPressureAltitude();
    for(int i = 1;i<scope.size();i++ )
    {
        if(altitudeGainPerSecond>(scope.get(i).getPressureAltitude()-before))
            return false;
        before = scope.get(i).getPressureAltitude();
    }
    return true;
    }
}
