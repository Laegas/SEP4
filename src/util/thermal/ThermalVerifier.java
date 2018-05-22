package util.thermal;

import model.outputData.IGCDataGroup;

public interface ThermalVerifier {
    boolean isThermalUsingPressureAltitude(IGCDataGroup points);
    boolean isThermalUsingGPSAltitude(IGCDataGroup points);
}
