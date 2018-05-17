package model.geography;

public class Second {
    private double second;


    public Second(double decimal)//constructor takes a decimal of a minute
    {
      setSecondWithDecimal(decimal);
    }

    public Second(int second)//constructor takes a second as argument
    {
        this.second = (double) second;
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

    public double getSecondAsDecimal()//returns a decimal of a minute of the amount of seconds
    {
        return second/60;
    }
}
