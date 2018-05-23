package util.thermal;

import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import java.util.List;

public interface ThermalFinder {
    List<ThermalDataPointGroup> findThermalsUsingGPSAltitude(Flight flight);

    List<ThermalDataPointGroup> findThermalsUsingPressureAltitude(Flight flight);
}



