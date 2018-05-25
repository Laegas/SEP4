package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindSpeed {
    private int konts;
    private boolean isValid;

    public WindSpeed(int knots) {
        setKonts(knots);
        this.isValid = true;
    }
    public WindSpeed() {
        this.konts = -1;
        this.isValid = false;
    }

    public int getKonts() throws Exception{
        if (!isValid)
            throw new Exception("not valid data");
        return konts;
    }

    public void setKonts(int konts) {
        if (konts < 0) {
            throw new RuntimeException("We can not have wind speed less than 0");
        }
        this.konts = konts;
    }
}
