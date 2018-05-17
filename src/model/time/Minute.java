package model.time;

import com.sun.javaws.exceptions.InvalidArgumentException;

public class Minute {

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
}
