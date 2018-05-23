package launchable;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.geography.LocationPoint;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.ThermalDataPointGroup;
import model.outputData.FeatureProperties;
import util.thermal.ThermalFinder;
import util.thermal.ThermalFinderImp;
import visualization.javaFxMaps.GenerateJson;

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

        for (Flight flight : flights) {

            Set<LocationPoint> thermalIndexSet = new HashSet<>();
            List<ThermalDataPointGroup> thermalDataPointGroups = thermalFinder.findThermalsUsingGPSAltitude(flight);
            for (ThermalDataPointGroup group : thermalDataPointGroups) {
                for (DataPoint dataPoint : group.getGroup()) {
                    thermalIndexSet.add(new LocationPoint(dataPoint.getLatitude(), dataPoint.getLongitude()));
                }
            }
            // add thermalIndexSet to output structure



        }





        FeatureProperties[][] outputData = null; // TODO fix this later

//        generateJson.generateJson(outputData); TODO
    }
}
