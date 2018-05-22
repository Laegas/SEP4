package model.outputData;

import model.igc.DataPoint;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class IGCDataGroup {
    List<DataPoint> dataPoints;
    int latGridIndex;
    int longGridIndex;

    public IGCDataGroup(List<DataPoint> dataPoints, int latIndex, int longIndex) {
        this.dataPoints = dataPoints;
        this.latGridIndex = latIndex;
        this.longGridIndex = longIndex;
    }

    public List<DataPoint> getDataPoints() {
        return dataPoints;
    }

    public int getLatGridIndex() {
        return latGridIndex;
    }

    public int getLongGridIndex() {
        return longGridIndex;
    }
}
