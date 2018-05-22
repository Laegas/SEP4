package model.outputData;

import config.VisualizationConfig;

/**
 * Created by kenneth on 22/05/2018.
 */
public class OutputData {
    private int numberOfRegisteredFlights;
    private int numberOfRegisteredThermal;

    public OutputData() {

        this.numberOfRegisteredThermal = 0;
        this.numberOfRegisteredFlights = 0;

    }

    private void incrementNumberOfRegisteredThermal() {
        numberOfRegisteredThermal++;
    }
    private void incrementNumberOfRegisteredFlights() {
        numberOfRegisteredFlights++;
    }

    public int getNumberOfRegisteredFlights() {
        return numberOfRegisteredFlights;
    }

    public int getNumberOfRegisteredThermal() {
        return numberOfRegisteredThermal;
    }

    /**
     * returns a number between 0 and 100
     * @return
     */
    public int getPercentageChanceOfThermal() {
        return (int) ((double) numberOfRegisteredThermal / (double) numberOfRegisteredFlights);
    }
}
