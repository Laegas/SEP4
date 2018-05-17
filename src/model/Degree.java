package model;

/**
 * Created by kenneth on 17/05/2018.
 */
public class Degree {
    private int degree;

    public Degree(int degree) {
        this.degree = degree;
    }

    public int getDegree() {
        return degree;
    }

    public void setDegree(int degree) {
        if (degree < 0 || degree > 360) {
            throw new RuntimeException("Not a valid degree");
        }
        this.degree = degree;
    }
}
