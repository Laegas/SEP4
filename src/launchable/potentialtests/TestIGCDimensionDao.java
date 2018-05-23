package launchable.potentialtests;

import database.DAO.DaoManager;
import model.igc.Flight;

import java.util.List;

/**
 * Created by kenneth on 18/05/2018.
 */
public class TestIGCDimensionDao {
    public static void main(String[] args) {
        List<Flight> flights = DaoManager.IGC_DIMENSIONAL_DAO.getAllFlights();
        System.out.println(flights.size());
        System.out.println(flights.get(0).getDatalog().size());
    }
}
