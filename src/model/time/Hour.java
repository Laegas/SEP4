package model.time;

public class Hour implements Comparable<Hour>{
    private int hour;
    public Hour(int hour)
    {
        setHour(hour);
    }

    @Override
    public String toString() {
        return "Hour{" +
                "hour=" + hour +
                '}';
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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Hour hour1 = (Hour) object;

        return hour == hour1.hour;
    }


}
