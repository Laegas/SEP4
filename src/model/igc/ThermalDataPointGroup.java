package model.igc;

import java.util.List;

public class ThermalDataPointGroup {
    private List<DataPoint> group;

    public ThermalDataPointGroup(List<DataPoint> group)
    {
        this.group = group;
    }

    public List<DataPoint> getGroup() {
        return group;
    }

    public void setGroup(List<DataPoint> group) {
        this.group = group;
    }
}
