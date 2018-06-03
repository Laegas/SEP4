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

        try {
            analyse(flights, outputData);
        } catch (InvalidCoordinatesException e) {
            System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
            e.printStackTrace();
        }

        int featureCount = GenerateJson.getInstance().generateJson(outputData);
        GenerateJSSettings.getInstance().generateSettings(outputData, featureCount);
    }

    public static void analyse(List<Flight> flights, OutputData outputData) throws InvalidCoordinatesException {

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
            List<LocationPoint> uniquePoints = RemoveDuplicate.getUniqueLocationPointsIndexes(thermalIndexes);

            for (LocationPoint locationPoint : uniquePoints) {
                try {
                    outputData.getFeatureProperties(locationPoint.getLatitude().getGridIndex(), locationPoint.getLongitude().getGridIndex()).getTotal().incrementRegisteredThermal();
                    // all thermals have been put into output data object
                } catch (InvalidCoordinatesException e) {
                    System.out.println("REMOVE ME WHEN ETL IS FINISHED. NIKI, GET YOUR SHIT TOGETHER");
                    e.printStackTrace();
                }
            }

            // get all unique location points from a flight and register them in output data object
            List<LocationPoint> allFromFlight = new ArrayList<>();
            for (DataPoint dataPoint : flight.getDatalog()) {
                allFromFlight.add(new LocationPoint(dataPoint.getLatitude(), dataPoint.getLongitude()));
            }
            List<LocationPoint> uniqueLocationPoints = RemoveDuplicate.getUniqueLocationPointsIndexes(allFromFlight);
            for (LocationPoint locationPoint : uniqueLocationPoints) {
                outputData.getFeatureProperties(locationPoint.getLatitude().getGridIndex(), locationPoint.getLongitude().getGridIndex()).getTotal().incrementRegisteredFlight();
                // registered all the visited grids for this flight
            }

        }

    }
}
