package launchable.potentialtests;

import database.DAO.DaoManager;
import model.igc.DataPoint;
import model.igc.Flight;

import java.util.ArrayList;
import java.util.List;

import static util.igc.RemoveDuplicate.getUniqueDataPoint;

public class RemoveDuplicateTest {
    public static void main(String[] args) {
        //test getUniqueDataPoint
        List<DataPoint> points = new ArrayList<>();

        List<Flight> flights = DaoManager.IGC_DIMENSIONAL_DAO.getAllFlights();
        points = flights.get(0).getDatalog();

        System.out.println("before size: " + points.size());
        List<DataPoint> dataPoints = getUniqueDataPoint(points);
        System.out.println("after size: "  + dataPoints.size());
    }
}
