package model.time;

public class Time {
    private Hour hour;
    private Minute minute;
    private Second second;

    public Time(Hour hour, Minute minute, Second second)
    {
        this.hour=hour;
        this.minute=minute;
        this.second=second;
    }

    public Time(int hour, int minute, int second)
    {
        this.hour= new Hour(hour);
        this.minute= new Minute(minute);
        this.second= new Second(second);
    }

    public Minute getMinute() {
        return minute;
    }

    public Hour getHour() {
        return hour;
    }

    public Second getSecond() {
        return second;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }

    public void setMinute(Minute minute) {
        this.minute = minute;
    }

    public void setSecond(Second second) {
        this.second = second;
    }
    
    public String toString()
    {
        return hour.getHour()+":"+minute.getMinute()+":"+second.getSecond();
    }
}

