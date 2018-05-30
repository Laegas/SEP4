package model.outputData;

import config.VisualizationConfig;
import gnu.regexp.RE;
import model.RegisteredFlightAndThermalCount;

/**
 * Created by kenneth on 22/05/2018.
 */
public class FeatureProperties {
    private RegisteredFlightAndThermalCount total;

    private RegisteredFlightAndThermalCount windDirZeroToNinty;
    private RegisteredFlightAndThermalCount windDirNintyToHundredEighty;
    private RegisteredFlightAndThermalCount windDirHundredEightyToTwoHundredSeventy;
    private RegisteredFlightAndThermalCount windDirTwoHundredSeventyToThreeHundredSizty;

//    private RegisteredFlightAndThermalCount betweenZeroAndTenDegree;
//    private RegisteredFlightAndThermalCount betweenTenAndTwentyDegree;
//    private RegisteredFlightAndThermalCount betweenTwentyAndThirtyDegree;

    public RegisteredFlightAndThermalCount getTotal() {
        return total;
    }
//    public RegisteredFlightAndThermalCount getBetweenZeroAndTenDegree() {
//        return betweenZeroAndTenDegree;
//    }
//    public RegisteredFlightAndThermalCount getBetweenTenAndTwentyDegree() {
//        return betweenTenAndTwentyDegree;
//    }
//    public RegisteredFlightAndThermalCount getBetweenTwentyAndThirtyDegree() {
//        return betweenTwentyAndThirtyDegree;
//    }

    public FeatureProperties() {
        this.total = new RegisteredFlightAndThermalCount(null);
        this.windDirHundredEightyToTwoHundredSeventy = new RegisteredFlightAndThermalCount("wind speed");
        this.windDirNintyToHundredEighty = new RegisteredFlightAndThermalCount("wind speed");
        this.windDirZeroToNinty = new RegisteredFlightAndThermalCount("wind speed");
        this.windDirTwoHundredSeventyToThreeHundredSizty = new RegisteredFlightAndThermalCount("wind speed");
//        this.betweenZeroAndTenDegree = new RegisteredFlightAndThermalCount();
//        this.betweenTenAndTwentyDegree = new RegisteredFlightAndThermalCount();
//        this.betweenTwentyAndThirtyDegree = new RegisteredFlightAndThermalCount();
    }

//    public void incrementZeroToTenFlight() {
//        this.betweenZeroAndTenDegree.incrementRegisteredFlight();
//    }
//    public void incrementZeroToTenThermal() {
//     this.betweenZeroAndTenDegree.incrementRegisteredThermal();
//    }
//    public int getZeroToTenFlight() {
//        return this.betweenZeroAndTenDegree.getNumberOfRegisteredFlights();
//    }
//    public int getZeroToTenThermal() {
//        return this.betweenZeroAndTenDegree.getNumberOfRegisteredThermal();
//    }
//    public int getZeroToTenPercentThermal() {
//        return (int) ((double) this.betweenZeroAndTenDegree.getNumberOfRegisteredThermal() / (double) this.betweenZeroAndTenDegree.getNumberOfRegisteredFlights());
//    }

//    public void incrementTotalNumberOfRegisteredThermal() {
//        this.total.incrementRegisteredThermal();;
//    }

//    public void incrementTotalNumberOfRegisteredFlights() {
//        this.total.incrementRegisteredFlight();
//    }

//    public int getTotalNumberOfRegisteredFlights() {
//        return this.total.getNumberOfRegisteredFlights();
//    }

//    public int getTotalNumberOfRegisteredThermal() {
//        return this.total.getNumberOfRegisteredThermal();
//    }

    /**
     * returns a number between 0 and 100
     * @return
     */
//    public int getTotalPercentageChanceOfThermal() {
//        return (int) ((double) this.total.getNumberOfRegisteredThermal() / (double) this.total.getNumberOfRegisteredFlights());
//    }
}
