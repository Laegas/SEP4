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
    private int thermalStart = -1;
    private int thermalEnd = -1;

    private ThermalDataPointGroup makeGroup(List<DataPoint> list, int start, int end)
    {
        ArrayList<DataPoint> glist = new ArrayList<>();
        System.out.println(start+" "+end);
        for(int i = start;i<end+1;i++)
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

            for (; index < list.size(); index++)
            {
                scope.add(list.get(index));
                if(scope.size()>10)
                    scope.remove(0);
                if(checkScopeUsingGPSAltitude(scope)&&thermalStart==-1)
                {
                    thermalStart=index-9;
                }
            }
            if(thermalStart==-1)
            {
                thermalEnd=index;
            }
            if(thermalStart!=-1)
            {
                group = makeGroup(list, thermalStart, thermalEnd);
                thermalGroups.add(group);
            }
        }
        return thermalGroups;
    }

    private boolean checkScopeUsingGPSAltitude(ArrayList<DataPoint> scope) {

        if(scope.size()<10)
            return false;
        double sum = 0;
        double firstNumber = scope.get(0).getGPSAltitude();
        for(int i =0;i<scope.size();i++)
        {
            sum+=scope.get(i).getGPSAltitude();
        }
        return 0<(sum/scope.size()-firstNumber);
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
                if(scope.size()>10)
                    scope.remove(0);
                if(checkScopeUsingPressureAltitude(scope))
                {
                    if(thermalStart==-1)thermalStart=index-9;
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

        if(scope.size()<=9)
            return false;
        double sum = 0;
        double firstNumber = scope.get(0).getPressureAltitude();
        for(int i =0;i<scope.size();i++)
        {
            sum+=scope.get(i).getPressureAltitude();
        }
        return 3<(sum/scope.size()-firstNumber);
    }
}
