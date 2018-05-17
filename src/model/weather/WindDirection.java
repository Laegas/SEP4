package model.weather;

import model.geography.Degree;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindDirection {
    Degree degree;

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public WindDirection(Degree degree) {

        this.degree = degree;
    }
}
