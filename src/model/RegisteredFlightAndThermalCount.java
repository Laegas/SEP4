package model;

import org.bridj.ann.Runtime;

/**
 * Created by kenneth on 24/05/2018.
 */
public class RegisteredFlightAndThermalCount {
    private int numberOfRegisteredFlights;
    private int numberOfRegisteredThermal;
    private Integer optionalParamater;
    private String optionalParameterName;

    /**
     * null if dont want to use optional paramater
     * @param optionalParamaterName
     */
    public RegisteredFlightAndThermalCount(String optionalParamaterName) {
        this.numberOfRegisteredFlights = 0;
        this.numberOfRegisteredThermal = 0;
        if (optionalParamaterName != null) {
            this.optionalParamater = 0;
            this.optionalParameterName = optionalParamaterName;
        }
    }
    public boolean usesOptionalParamater() {
        return this.optionalParameterName != null;
    }

    public void setOptionalParamater(int value) {
        if (!usesOptionalParamater()) {
            throw new RuntimeException("this object does not use the optional paramater");
        }
        this.optionalParamater = value;
    }

    public int getOptionalParamater() {
        if (!usesOptionalParamater()) {
            throw new RuntimeException("this object does not use the optional paramater");
        }
        return this.optionalParamater;
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
