package launchable.analysis;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.geography.InvalidCoordinatesException;
import model.geography.LocationPoint;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import model.outputData.OutputData;
import util.igc.RemoveDuplicate;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;
import visualization.javaFxMaps.GenerateJSSettings;
import visualization.javaFxMaps.GenerateJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class CountThermalsAnalysis {
    public static void main(String[] args) {

        IGCDimensionalDao igcDAO = DaoManager.IGC_DIMENSIONAL_DAO;
        List<Flight> flights = igcDAO.getAllFlights();
        System.out.println("number of flights " + flights.size());

        // some output data structure
        OutputData outputData = new OutputData();

        analayse(flights, outputData);

        GenerateJson.getInstance().generateJson(outputData);
    }

    public static void analayse(List<Flight> flights, OutputData outputData) {

        ThermalFinder thermalFinder = new ThermalFinderImp();
        for (Flight flight : flights) {

            List<LocationPoint> thermalIndexes = new ArrayList<>();
            List<ThermalDataPointGroup> thermalDataPointGroups = thermalFinder.findThermalsUsingPressureAltitude(flight);
            for (ThermalDataPointGroup group : thermalDataPointGroups) {
                for (DataPoint dataPoint : group.getGroup()) {
                    thermalIndexes.add(new LocationPoint(dataPoint.getLatitude(), dataPoint.getLongitude()));
                }
            }
            // add thermalIndexSet to output structure
            List<LocationPoint> uniquePoints = RemoveDuplicate.getUniqueLocationPointsIndexe(thermalIndexes);

            for (LocationPoint locationPoint : uniquePoints) {
                outputData.getFeatureProperties(locationPoint.getLatitude().getGridIndex(), locationPoint.getLongitude().getGridIndex()).getTotal().incrementRegisteredThermal();
                // all thermals have been put into output data object
            }

            // get all unique location points from a flight and register them in output data object
            List<LocationPoint> allFromFlight = new ArrayList<>();
            for (DataPoint dataPoint : flight.getDatalog()) {
                allFromFlight.add(new LocationPoint(dataPoint.getLatitude(), dataPoint.getLongitude()));
            }
            List<LocationPoint> uniqueLocationPoints = RemoveDuplicate.getUniqueLocationPointsIndexe(allFromFlight);
            for (LocationPoint locationPoint : uniqueLocationPoints) {
                try {
                    outputData.getFeatureProperties(locationPoint.getLatitude().getGridIndex(), locationPoint.getLongitude().getGridIndex()).getTotal().incrementRegisteredFlight();
                } catch (InvalidCoordinatesException e) {
                    e.printStackTrace();
                }
                // registered all the visited grids for this flight
            }

        }

    }
}
