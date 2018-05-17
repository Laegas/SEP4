package model.time;

/**
 * Created by kenneth on 17/05/2018.
 */
public class Day {
    private int dayOfMonth;

    public Day(int dayOfMonth) {
        setDayOfMonth(dayOfMonth);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(int dayOfMonth) {
        if (dayOfMonth < 1 || dayOfMonth > 31) {
            throw new RuntimeException("Not a valid day of month");
        }

        this.dayOfMonth = dayOfMonth;
    }
}
