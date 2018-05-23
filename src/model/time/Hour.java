package model.time;

public class Hour implements Comparable<Hour>{
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

    @Override
    public int compareTo(Hour o) {
        if (this.hour > o.getHour()) {
            return 1;
        } else if (this.hour == o.getHour()) {
            return 0;
        } else {
            return -1;
        }
    }
}
