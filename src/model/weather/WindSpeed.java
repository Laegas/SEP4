package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindSpeed {
    private int konts;

    public WindSpeed(int knots) {
        setKonts(knots);
    }
    public WindSpeed() {
        this.konts = -1;
    }

    public int getKonts() throws Exception{
        return konts;
    }

    public void setKonts(int konts) {
        if (konts < 0) {
            throw new RuntimeException("We can not have wind speed less than 0");
        }
        this.konts = konts;
    }
}
