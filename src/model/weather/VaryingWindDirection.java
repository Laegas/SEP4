package model.weather;

import model.geography.Degree;

/**
 * Created by kenneth on 17/05/2018.
 */
public class VaryingWindDirection {
    private Degree from;
    private Degree to;

    public Degree getFrom() {
        return from;
    }

    public void setFrom(Degree from) {
        this.from = from;
    }

    public Degree getTo() {
        return to;
    }

    public void setTo(Degree to) {
        this.to = to;
    }

    public VaryingWindDirection(Degree from, Degree to) {
        this.from = from;
        this.to = to;
    }
}
