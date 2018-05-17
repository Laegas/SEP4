package model.time;

/**
 * Created by kenneth on 17/05/2018.
 */
public class Month {
    private int monthNumber;

    public int getMonthNumber() {
        return monthNumber;
    }

    public void setMonthNumber(int monthNumber) {
        if (monthNumber < 1 || monthNumber > 12) {
            throw new RuntimeException("now a valid month number");
        }
        this.monthNumber = monthNumber;
    }

    public Month(int monthNumber) {

        setMonthNumber(monthNumber);
    }
}
