package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class ICAOAirportCode {
    private String ICAOCode;

    public ICAOAirportCode(String ICAOCode) {
        this.ICAOCode = ICAOCode;
    }

    public String getICAOCode() {
        return ICAOCode;
    }

    public void setICAOCode(String ICAOCode) {
        this.ICAOCode = ICAOCode;
    }
}
