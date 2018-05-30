package model.outputData;

import config.VisualizationConfig;

import java.util.Iterator;

/**
 * Created by kenneth on 23/05/2018.
 */
public class OutputData implements Iterable<FeatureProperties>{

    FeatureProperties[][] featureProperties;


    /**
     * returns a vlaue 2/3 ed of the max value
     * @return
     */
    public int getMaxTotalRegisteredFlightCount() {

        int currentMax = -1;
        for (int h = 0; h < featureProperties.length; h++) {
            for (int w = 0; w < featureProperties[0].length; w++) {
                if (featureProperties[h][w].getTotal().getNumberOfRegisteredFlights() > currentMax) {
                    currentMax = featureProperties[h][w].getTotal().getNumberOfRegisteredFlights();
                }
            }
        }

        return (int)(currentMax*2.0/3.0);
    }

    public FeatureProperties getFeatureProperties(int latIndex, int longIndex) {
        return featureProperties[latIndex][longIndex];
    }

    public OutputData() {
        this.featureProperties = new FeatureProperties[VisualizationConfig.HEIGHT][VisualizationConfig.WIDTH];
        // initialising all feature properties
        for (int h = 0; h < VisualizationConfig.HEIGHT; h++) {
            for (int w = 0 ; w < VisualizationConfig.WIDTH; w++) {
                this.featureProperties[h][w] = new FeatureProperties();
            }
        }

    }



    @Override
    public Iterator<FeatureProperties> iterator() {
        return new OutputDataIterator(this.featureProperties);
    }

    public FeatureProperties[][] getFeatureProperties() {
        return featureProperties;
    }

    private class OutputDataIterator implements Iterator<FeatureProperties> {
        private FeatureProperties[][] featureProperties;
        private int height;
        private int width;


        private FeatureProperties tempFeatureProperties;

        public OutputDataIterator(FeatureProperties[][] featureProperties) {
            this.featureProperties = featureProperties;
            this.height = 0;
            this.width = 0;
        }

        @Override
        public boolean hasNext() {
            return (width < VisualizationConfig.WIDTH && height < VisualizationConfig.HEIGHT);
        }

        @Override
        public FeatureProperties next() {
            if (hasNext()) {
                this.tempFeatureProperties = this.featureProperties[height][width++];

                if (this.width == VisualizationConfig.WIDTH) {
                    this.width = 0;
                    this.height++;
                }
                return tempFeatureProperties;
            } else {
                return null;
            }
        }
    }
}

