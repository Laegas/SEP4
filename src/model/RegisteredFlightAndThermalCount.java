package model;

/**
 * Created by kenneth on 24/05/2018.
 */
public class RegisteredFlightAndThermalCount {
    private int numberOfRegisteredFlights;
    private int numberOfRegisteredThermal;

    public RegisteredFlightAndThermalCount() {
        this.numberOfRegisteredFlights = 0;
        this.numberOfRegisteredThermal = 0;
    }

    public int getNumberOfRegisteredFlights() {
        return numberOfRegisteredFlights;
    }

    public int getNumberOfRegisteredThermal() {
        return numberOfRegisteredThermal;
    }
    public void incrementRegisteredThermal() {
        this.numberOfRegisteredThermal++;
    }
    public void incrementRegisteredFlight() {
        this.numberOfRegisteredFlights++;
    }

    /**
     * returns an interger between 0 and 100
     */
    public int getPercentage() {
        return (int) ((double) numberOfRegisteredThermal / (double) numberOfRegisteredFlights * 100);
    }

    public void setNumberOfRegisteredFlights(int numberOfRegisteredFlights) {
        this.numberOfRegisteredFlights = numberOfRegisteredFlights;
    }

    public void setNumberOfRegisteredThermal(int numberOfRegisteredThermal) {
        this.numberOfRegisteredThermal = numberOfRegisteredThermal;
    }
}
