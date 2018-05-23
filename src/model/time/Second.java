package model.time;

public class Second implements Comparable<Second>{
    private int second;

    public Second(int second)
    {
        setSecond(second);
    }


    public void setSecond(int second)
    {
        if(second<0||second>59)
            throw new RuntimeException("Value of "+second+" is invalid. It has to be more than or equals 0 and less than 60");
        this.second=second;
    }

    public int getSecond()
    {
        return second;
    }

    @Override
    public int compareTo(Second o) {

        if (this.second > o.getSecond()) {
            return 1;
        } else if (this.second == o.getSecond()) {
            return 0;
        } else {
            return -1;
        }
    }
}
