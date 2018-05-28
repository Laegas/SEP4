package model.outputData;

import config.VisualizationConfig;
import model.RegisteredFlightAndThermalCount;

/**
 * Created by kenneth on 22/05/2018.
 */
public class FeatureProperties {
    private RegisteredFlightAndThermalCount total;

    public RegisteredFlightAndThermalCount getTotal() {
        return total;
    }

    public RegisteredFlightAndThermalCount getBetweenZeroAndTenDegree() {
        return betweenZeroAndTenDegree;
    }

    public RegisteredFlightAndThermalCount getBetweenTenAndTwentyDegree() {
        return betweenTenAndTwentyDegree;
    }

    public RegisteredFlightAndThermalCount getBetweenTwentyAndThirtyDegree() {
        return betweenTwentyAndThirtyDegree;
    }

    private RegisteredFlightAndThermalCount betweenZeroAndTenDegree;
    private RegisteredFlightAndThermalCount betweenTenAndTwentyDegree;
    private RegisteredFlightAndThermalCount betweenTwentyAndThirtyDegree;


    public FeatureProperties() {
        this.total = new RegisteredFlightAndThermalCount();
        this.betweenZeroAndTenDegree = new RegisteredFlightAndThermalCount();
        this.betweenTenAndTwentyDegree = new RegisteredFlightAndThermalCount();
        this.betweenTwentyAndThirtyDegree = new RegisteredFlightAndThermalCount();
    }
    public void incrementZeroToTenFlight() {
        this.betweenZeroAndTenDegree.incrementRegisteredFlight();
    }
    public void incrementZeroToTenThermal() {
     this.betweenZeroAndTenDegree.incrementRegisteredThermal();
    }
    public int getZeroToTenFlight() {
        return this.betweenZeroAndTenDegree.getNumberOfRegisteredFlights();
    }
    public int getZeroToTenThermal() {
        return this.betweenZeroAndTenDegree.getNumberOfRegisteredThermal();
    }
    public int getZeroToTenPercentThermal() {
        return (int) ((double) this.betweenZeroAndTenDegree.getNumberOfRegisteredThermal() / (double) this.betweenZeroAndTenDegree.getNumberOfRegisteredFlights());
    }


    public void incrementTotalNumberOfRegisteredThermal() {
        this.total.incrementRegisteredThermal();;
    }
    public void incrementTotalNumberOfRegisteredFlights() {
        this.total.incrementRegisteredFlight();
    }

    public int getTotalNumberOfRegisteredFlights() {
        return this.total.getNumberOfRegisteredFlights();
    }

    public int getTotalNumberOfRegisteredThermal() {
        return this.total.getNumberOfRegisteredThermal();
    }

    /**
     * Used for evaluating if a feature should be added to visualization.
     *
     * @return true if feature properties are meaningful to show in visualization.
     */
    public boolean isMeaningful() {
        if(total.getNumberOfRegisteredFlights()==0)
            return false;
        return true;
//        return  total.getNumberOfRegisteredFlights()>8 && ((double)total.getNumberOfRegisteredThermal()/(double)total.getNumberOfRegisteredFlights()*100)>30.0;
//        return Math.random() > 0.9;
//        return (numberOfRegisteredThermal > 0);

    }

    /**
     * returns a number between 0 and 100
     * @return
     */
    public int getTotalPercentageChanceOfThermal() {
        return (int) ((double) this.total.getNumberOfRegisteredThermal() / (double) this.total.getNumberOfRegisteredFlights());
    }
}
