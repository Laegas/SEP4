package launchable;

import com.sun.xml.internal.bind.v2.TODO;
import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.igc.Flight;
import model.outputData.IGCDataGroup;
import model.outputData.OutputData;
import util.flight.FlightSplitter;
import util.flight.FlightSplitterImp;
import util.thermal.ThermalVerifier;
import util.thermal.ThermalVerifierImp;
import visualization.javaFxMaps.GenerateJson;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class CountThermalsAnalasys {
    public static void main(String[] args) {
        GenerateJson generateJson = GenerateJson.getInstance();

        ThermalVerifier thermalTester = new ThermalVerifierImp();
        FlightSplitter splitter = new FlightSplitterImp();

        IGCDimensionalDao igcDAO = DaoManager.IGC_DIMENSIONAL_DAO;
        List<Flight> flights = igcDAO.getAllFlights();


        OutputData[][] outputData = null; // TODO fix this later

        for (Flight flight : flights) {
            List<IGCDataGroup> dataGroups = splitter.splitFlight(flight);
            for (IGCDataGroup dataGroup : dataGroups) {
                if (thermalTester.isThermalUsingGPSAltitude(dataGroup)) {
                    System.out.println("thermal at, long: " + dataGroup.getLongGridIndex() + ", lat: " + dataGroup.getLatGridIndex());
//                    outputData[dataGroup.getLongGridIndex()][dataGroup.getLatGridIndex()].incrementNumberOfRegisteredThermal();
//                    outputData[dataGroup.getLongGridIndex()][dataGroup.getLatGridIndex()].incrementNumberOfRegisteredFlights();
                } else {
//                    outputData[dataGroup.getLongGridIndex()][dataGroup.getLatGridIndex()].incrementNumberOfRegisteredFlights();
                }

            }

        }


//        generateJson.generateJson(outputData); TODO
    }
}
