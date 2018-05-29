package model.weather;

public class WMOIndex {
    private String WMOIndex;

    public WMOIndex(String WMO)
    {
        setWMOIndex(WMO);
    }

    public void setWMOIndex(String WMOIndex) {
        this.WMOIndex = WMOIndex;
    }

    public String getWMOIndex() {
        return WMOIndex;
    }
}
