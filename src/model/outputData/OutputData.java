package model.outputData;

import config.VisualizationConfig;

/**
 * Created by kenneth on 22/05/2018.
 */
public class OutputData {
    private int[][] numberOfRegisteredFlights;
    private int[][] numberOfRegisteredThermal;

    public OutputData() {

        this.numberOfRegisteredThermal = new int[VisualizationConfig.WIDTH][VisualizationConfig.HEIGHT];
        this.numberOfRegisteredFlights = new int[VisualizationConfig.WIDTH][VisualizationConfig.HEIGHT];
    }

    private void incrementNumberOfRegisteredThermal( int longIndex, int latIndex) {
        numberOfRegisteredThermal[longIndex][latIndex]++;
    }
    private void incrementNumberOfRegisteredFlights( int longIndex, int latIndex) {
        numberOfRegisteredFlights[longIndex][latIndex]++;
    }

    public int[][] getNumberOfRegisteredFlights() {
        return numberOfRegisteredFlights;
    }

    public int[][] getNumberOfRegisteredThermal() {
        return numberOfRegisteredThermal;
    }
}
