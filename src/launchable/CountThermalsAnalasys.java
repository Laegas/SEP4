package launchable;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDao;
import model.igc.Flight;
import model.outputData.IGCDataGroup;
import model.outputData.OutputData;
import util.flight.FlightSplitter;
import util.flight.FlightSplitterImp;
import util.thermal.ThermalTester;
import util.thermal.ThermalTesterImp;
import visualization.javaFxMaps.GenerateJson;

import java.util.List;

/**
 * Created by kenneth on 22/05/2018.
 */
public class CountThermalsAnalasys {
    public static void main(String[] args) {
        GenerateJson generateJson = GenerateJson.getInstance();

        ThermalTester thermalTester = new ThermalTesterImp();
        FlightSplitter splitter = new FlightSplitterImp();

        IGCDimensionalDao igcDAO = DaoManager.IGC_DIMENSIONAL_DAO;
        List<Flight> flights = igcDAO.getAllFlights();


        OutputData[][] outputData = null; // TODO fix this later

        for (Flight flight : flights) {
            List<IGCDataGroup> dataGroups = splitter.splitFlight(flight);
            for (IGCDataGroup dataGroup : dataGroups) {
//                if (thermalTester.isThermal(dataGroup)) { TODO w8 for thermalTester.isThermal to be updated
                if(false){
                    outputData[dataGroup.getLongGridIndex()][dataGroup.getLatGridIndex()].incrementNumberOfRegisteredThermal();
                    outputData[dataGroup.getLongGridIndex()][dataGroup.getLatGridIndex()].incrementNumberOfRegisteredFlights();
                } else {
                    outputData[dataGroup.getLongGridIndex()][dataGroup.getLatGridIndex()].incrementNumberOfRegisteredFlights();
                }

            }

        }


//        generateJson.generateJson(outputData); TODO
    }
}
