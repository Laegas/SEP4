package launchable;

import com.sun.xml.internal.bind.v2.TODO;
import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.geography.InvalidCoordinatesException;
import model.geography.LocationPoint;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import model.outputData.FeatureProperties;
import model.outputData.OutputData;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;
import visualization.InvalidGridDimensionException;
import visualization.javaFxMaps.GenerateJson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by kenneth on 22/05/2018.
 */
public class CountThermalsAnalasys {
    public static void main(String[] args) {
        GenerateJson generateJson = GenerateJson.getInstance();

        IGCDimensionalDao igcDAO = DaoManager.IGC_DIMENSIONAL_DAO;
        List<Flight> flights = igcDAO.getAllFlights();
        ThermalFinder thermalFinder = new ThermalFinderImp();

        // some output data structure
        OutputData outputData = new OutputData();

        for (Flight flight : flights) {

            Set<LocationPoint> thermalIndexSet = new HashSet<>();
            List<ThermalDataPointGroup> thermalDataPointGroups = thermalFinder.findThermalsUsingGPSAltitude(flight);
            for (ThermalDataPointGroup group : thermalDataPointGroups) {
                for (DataPoint dataPoint : group.getGroup()) {
                    thermalIndexSet.add(new LocationPoint(dataPoint.getLatitude(), dataPoint.getLongitude()));
                }
            }
            // add thermalIndexSet to output structure

            for (LocationPoint locationPoint : thermalIndexSet) {
                outputData.registerThermalAtIndex(locationPoint.getLatitude().getGridIndex(),locationPoint.getLongitude().getGridIndex());
                // all thermals have been put into output data object
            }

            // get all unique location points from a flight and register them in outputdata object
            Set<LocationPoint> allFromFlight = new HashSet<>();
            for (DataPoint dataPoint : flight.getDatalog()) {
                allFromFlight.add(new LocationPoint(dataPoint.getLatitude(), dataPoint.getLongitude()));
            }

            for (LocationPoint locationPoint : allFromFlight) {
                try {
                    outputData.registerFlightAtIndex(locationPoint.getLatitude().getGridIndex(), locationPoint.getLongitude().getGridIndex());
                } catch (InvalidCoordinatesException e) {

                }
                // registered all the visited grids for this flight
            }

        }

        try {
            generateJson.generateJson(outputData);
        } catch (InvalidGridDimensionException e) {
            e.printStackTrace();
        }
    }
}
