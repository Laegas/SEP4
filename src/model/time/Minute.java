package model.time;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Minute implements Comparable<Minute>{

    @Override
    public String toString() {
        return "Minute{" +
                "minute=" + minute +
                '}';
    }

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
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Minute))
            return false;
        Minute o = (Minute)obj;
        return o.minute == (this.minute);
    }

}
