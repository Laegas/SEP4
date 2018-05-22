package util.thermal;

import model.igc.DataPoint;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public interface ThermalTester {
    boolean isThermal(List<DataPoint> dataPoints);
}
