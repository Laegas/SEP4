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

    public Time(int seconds) {
        int hour = (int) (seconds / 3600);
        int remain_sec = seconds - hour * 3600;
        int minute = (int) (remain_sec / 60);
        remain_sec = remain_sec - minute * 60;

        setHour(new Hour(hour));
        setMinute(new Minute(minute));
        setSecond(new Second(remain_sec));
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

    public int getTotalSeconds() {
        return hour.getHour() * 3600 + minute.getMinute() * 60 + second.getSecond();
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


    public Time timeBetween(Time other) {
        int res_hour = this.getHour().getHour() - other.getHour().getHour();
        int res_min = this.getMinute().getMinute() - other.getMinute().getMinute();
        int res_sec = this.getSecond().getSecond() - other.getSecond().getSecond();

        int nr_of_sec = res_hour * 60 * 60 + res_min * 60 + res_sec;
        nr_of_sec = Math.abs(nr_of_sec);

        return new Time(nr_of_sec);
    }

    public static void main(String[] args) {
        Time t1 = new Time(15, 40, 12);
        Time t2 = new Time(10, 20, 10);

        System.out.println(t1.timeBetween(t2));
        System.out.println(t2.timeBetween(t1));
    }

    @Override
    public String toString() {
        return "Time{" +
                "hour=" + hour +
                ", minute=" + minute +
                ", second=" + second +
                '}';
    }
}

