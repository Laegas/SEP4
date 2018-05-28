package model.time;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Minute implements Comparable<Minute>{

    private int minute;

    public Minute(int minute)
    {
        setMinute(minute);
    }

    public int getMinute()
    {
        return minute;
    }

    public void setMinute(int minute)
    {
        if(minute<0||minute>59)
            throw new RuntimeException("Value of "+minute+" is invalid. It has to be more than or equals 0 and less than 60");
        this.minute=minute;
    }

    @Override
    public int compareTo(Minute o) {

        if (this.minute > o.getMinute()) {
            return 1;
        } else if (this.minute == o.getMinute()) {
            return 0;
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Minute minute1 = (Minute) object;

        return minute == minute1.minute;
    }


}
