package model.outputData;

import config.VisualizationConfig;

/**
 * Created by kenneth on 22/05/2018.
 */
public class FeatureProperties {
    private int numberOfRegisteredFlights;
    private int numberOfRegisteredThermal;

    public FeatureProperties() {

        this.numberOfRegisteredThermal = 0;
        this.numberOfRegisteredFlights = 0;

    }

    public void incrementNumberOfRegisteredThermal() {
        numberOfRegisteredThermal++;
    }
    public void incrementNumberOfRegisteredFlights() {
        numberOfRegisteredFlights++;
    }

    public int getNumberOfRegisteredFlights() {
        return numberOfRegisteredFlights;
    }

    public int getNumberOfRegisteredThermal() {
        return numberOfRegisteredThermal;
    }

    /**
     * Used for evaluating if a feature should be added to visualization.
     *
     * @return true if feature properties are meaningful to show in visualization.
     */
    public boolean isMeaningful() {
        return numberOfRegisteredFlights > 0;
//        return Math.random() > 0.9;
//        return (numberOfRegisteredThermal > 0);
    }


    /**
     * returns a number between 0 and 100
     * @return
     */
    public int getPercentageChanceOfThermal() {
        return (int) ((double) numberOfRegisteredThermal / (double) numberOfRegisteredFlights);
    }
}
