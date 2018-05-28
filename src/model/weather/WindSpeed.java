package model.weather;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindSpeed {
    private int knots;

    public WindSpeed(int knots) {
        setKnots(knots);
    }
    public WindSpeed() {
        this.knots = -1;
    }

    public int getKnots() throws Exception{
        return knots;
    }

    public void setKnots(int knots) {
        if (knots < 0) {
            throw new RuntimeException("We can not have wind speed less than 0");
        }
        this.knots = knots;
    }
}
