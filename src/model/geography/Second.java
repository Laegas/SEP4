package model.geography;

public class Second {
    private double second;


    public Second(double second)//constructor takes a decimal of a minute
    {
      this.second = second;
    }

    public void setSecond(double second)//normal setter
    {
        this.second=second;
    }
    public void setSecondWithDecimal(double decimal)
    {
        this.second = decimal * 60; //turns the decimal of a minute into seconds
    }

    public double getSecond()
    {
        return second;
    }

    @Override
    public String toString() {
        return "Second{" +
                "second=" + second +
                '}';
    }

    public double getSecondAsDecimal()//returns a decimal of a minute of the amount of seconds
    {
        return second/60;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(!(obj instanceof Second))
            return false;
        Second o = (Second)obj;
        return o.second == (this.second);
    }
}
