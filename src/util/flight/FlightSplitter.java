package util.flight;

import model.igc.Flight;
import model.outputData.IGCDataGroup;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public interface FlightSplitter {
    List<IGCDataGroup> splitFlight(Flight flight);
}
