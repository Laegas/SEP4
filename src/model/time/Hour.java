package model.time;

public class Hour {
    private int hour;
    public Hour(int hour)
    {
        setHour(hour);
    }

    public void setHour(int hour)
    {
        if(hour<0 || hour>23)
            throw new RuntimeException("Value of "+ hour+ " is invalid. Value has to be between 0 and 24 excluding 24");
        this.hour=hour;
    }
    public int getHour()
    {
        return hour;
    }

}
