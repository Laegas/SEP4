package model.time;

public class Time implements Comparable<Time>{
    private Hour hour;
    private Minute minute;
    private Second second;

    public Time(Hour hour, Minute minute, Second second)
    {
        this.hour=hour;
        this.minute=minute;
        this.second=second;
    }

    public Time(java.sql.Time time) {
        this(time.getHours(), time.getMinutes(), time.getSeconds());
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

    @Override
    public int compareTo(Time o) {
        if (this.hour.compareTo(o.getHour()) > 0) {
            return 1;
        } else if (this.hour.compareTo(o.getHour()) < 0){
            return -1;

        } else if (this.hour.compareTo(o.getHour()) == 0) {
            if (this.minute.compareTo(o.getMinute()) > 0) {
                return 1;
            } else if (this.minute.compareTo(o.getMinute()) < 0) {
                return -1;
            } else if (this.minute.compareTo(o.getMinute()) == 0) {
                if (this.second.compareTo(o.getSecond()) > 0) {
                    return 1;
                } else if (this.second.compareTo(o.getSecond()) < 0) {
                    return -1;
                } else if (this.second.compareTo(o.getSecond()) == 0) {
                    return 0;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Time time = (Time) object;

        if (!hour.equals(time.hour)) return false;
        if (!minute.equals(time.minute)) return false;
        return second.equals(time.second);
    }


}

