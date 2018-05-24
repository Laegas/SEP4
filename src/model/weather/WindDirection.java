package model.weather;

import model.geography.Degree;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindDirection {
    private Degree degree;
    private boolean variable;

    public WindDirection(Degree degree) {
        this.degree = degree;
        this.variable = false;
    }

    public WindDirection() {
        this.degree = null;
        this.variable = false;
    }

    public Degree getDegree() throws Exception {
        if(variable)
            throw new Exception("Wind direction is variable");
        return degree;
    }
}
