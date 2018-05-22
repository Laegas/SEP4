package util.flight;

import model.igc.DataPoint;
import model.igc.Flight;
import model.outputData.IGCDataGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class FlightSplitterImp implements FlightSplitter {
    @Override
    public List<IGCDataGroup> splitFlight(Flight flight) {
        List<IGCDataGroup> igcDataGroups = new ArrayList<>();

        List<DataPoint> tmpgrp = null;
        int latGridIndex = -1;
        int longGridIndex = -1;
        for (DataPoint dataPoint : flight.getDatalog()) {
            if (latGridIndex == -1 && longGridIndex == -1) {
                tmpgrp = new ArrayList<>();
                latGridIndex = dataPoint.getLatitude().getGridIndex();
                longGridIndex = dataPoint.getLongitude().getGridIndex();
            } else {
                if (dataPoint.getLongitude().getGridIndex() == longGridIndex && dataPoint.getLatitude().getGridIndex() == latGridIndex) {
                    tmpgrp.add(dataPoint);
                } else {
                    // this is the end of this group
                    igcDataGroups.add(new IGCDataGroup(tmpgrp, latGridIndex, longGridIndex));
                    tmpgrp = new ArrayList<>();
                    latGridIndex = dataPoint.getLatitude().getGridIndex();
                    longGridIndex = dataPoint.getLongitude().getGridIndex();

                    //now the next group have started
                    tmpgrp.add(dataPoint);
                }
            }

        }
        return igcDataGroups;
    }
}
