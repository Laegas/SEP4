package model.outputData;

import config.VisualizationConfig;

/**
 * Created by kenneth on 24/05/2018.
 */
@Deprecated
public class SingleFlightOutputData {
    FeatureProperties[][] featureProperties;


    public SingleFlightOutputData() {
        this.featureProperties = new FeatureProperties[VisualizationConfig.HEIGHT][VisualizationConfig.WIDTH];

        for (FeatureProperties[] featureProperties : featureProperties) {
            for (FeatureProperties item : featureProperties) {
                item = new FeatureProperties();
            }
        }
    }

    public void setFeaturePropertiesTotalFlightToOne(int latIndex, int longIndex) {
        featureProperties[latIndex][longIndex].getTotal().setNumberOfRegisteredFlights(1);
    }
    public void setFeaturePropertiesTotalThermalToOne(int latIndex,int longIndex) {
        featureProperties[latIndex][longIndex].getTotal().setNumberOfRegisteredThermal(1);
    }
    public void setFeaturePropertiesBetweenZeroAndTenFlightToOne(int latIndex, int longIndex) {
        featureProperties[latIndex][longIndex].getBetweenZeroAndTenDegree().setNumberOfRegisteredFlights(1);
    }
    public void setFeaturePropertiesBetweenZeroAndTenThermalToOne(int latIndex,int longIndex) {
        featureProperties[latIndex][longIndex].getBetweenZeroAndTenDegree().setNumberOfRegisteredThermal(1);
    }
    public void setFeaturePropertiesBetweenTenAndTwentyFlightToOne(int latIndex, int longIndex) {
        featureProperties[latIndex][longIndex].getBetweenTenAndTwentyDegree().setNumberOfRegisteredFlights(1);
    }
    public void setFeaturePropertiesBetweenTenAndTwentyThermalToOne(int latIndex,int longIndex) {
        featureProperties[latIndex][longIndex].getBetweenTenAndTwentyDegree().setNumberOfRegisteredThermal(1);
    }
    public void setFeaturePropertiesBetweenTwentyAndThirtyFlightToOne(int latIndex, int longIndex) {
        featureProperties[latIndex][longIndex].getTotal().setNumberOfRegisteredFlights(1);
    }
    public void setFeaturePropertiesBetweenTwentyAndThrityThermalToOne(int latIndex,int longIndex) {
        featureProperties[latIndex][longIndex].getBetweenTwentyAndThirtyDegree().setNumberOfRegisteredThermal(1);
    }

}
