package database.DAO;

import model.igc.DataPoint;
import model.igc.Flight;
import model.weather.Airport;

import java.util.List;

/**
 * Created by kenneth on 18/05/2018.
 */
public interface IGCDimensionalDao {

    public List<Flight> getAllFlights();

    public void setClosestAirport(DataPoint dataPoint, Airport airport);

}
