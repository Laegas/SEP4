package util.flight.test;

import database.DAO.DaoManager;
import model.igc.Flight;
import util.flight.FlightSplitter;
import util.flight.FlightSplitterImp;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class Test {
    public static void main(String[] args) {
        FlightSplitter splitter = new FlightSplitterImp();
        List<Flight> flights = DaoManager.IGC_DIMENSIONAL_DAO.getAllFlights();
        for (Flight flight : flights) {
            System.out.println(splitter.splitFlight(flight).size());

        }
    }
}
