package model.outputData;

import model.igc.DataPoint;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class IGCDataGroup {
    List<DataPoint> dataPoints;

    public IGCDataGroup(List<DataPoint> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }
}
