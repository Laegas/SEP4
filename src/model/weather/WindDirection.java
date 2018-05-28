package model.weather;

import model.geography.Degree;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WindDirection {
    private Degree degree;
    private boolean variable;

    public WindDirection(Degree degree) {
        setDegree(degree);
        this.variable = false;
    }

    public void setDegree(Degree degree) {
        if (degree.getDegree() < 0) {
            this.variable = true;
        }
        this.degree = degree;
    }

    public WindDirection() {
        this.degree = null;
        this.variable = true;
    }

    public Degree getDegree() throws Exception{
        if(variable)
           throw new Exception("the degree is not measurable");
        return degree;
    }
}
