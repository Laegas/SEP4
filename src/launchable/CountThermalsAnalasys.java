package launchable;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.igc.Flight;
import model.outputData.OutputData;
import util.flight.FlightSplitter;
import util.flight.FlightSplitterImp;
import visualization.javaFxMaps.GenerateJson;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class CountThermalsAnalasys {
    public static void main(String[] args) {
        GenerateJson generateJson = GenerateJson.getInstance();

//        ThermalVerifier thermalTester = new ThermalVerifierImp();
        FlightSplitter splitter = new FlightSplitterImp();

        IGCDimensionalDao igcDAO = DaoManager.IGC_DIMENSIONAL_DAO;
        List<Flight> flights = igcDAO.getAllFlights();


        OutputData[][] outputData = null; // TODO fix this later

//        generateJson.generateJson(outputData); TODO
    }
}
