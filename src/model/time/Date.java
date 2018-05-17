package model.time;

public class Date {
    private Day day;
    private Month month;
    private Year year;

    public Date(int day, int month, int year)
    {
        this.day = new Day(day);
        this.month = new Month(month);
        this.year = new Year(year);
    }

    public Date(Day day, Month month, Year year)
    {
        this.day= day;
        this.month=month;
        this.year = year;
    }

    public Day getDay() {
        return day;
    }

    public Month getMonth() {
        return month;
    }

    public Year getYear() {
        return year;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
