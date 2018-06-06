package launchable;

import launchable.fileloaders.ClosestAirport;
import launchable.fileloaders.MainFileLoader;
import launchable.sqlRunner.SQLRunner;

/**
 * Created by kenneth on 05/06/2018.
 */
public class AllTheStuff {
    public static void main(String[] args) {
        SQLRunner sqlRunner = new SQLRunner();
        MainFileLoader mainFileLoader = new MainFileLoader();
        ClosestAirport closestAirport = new ClosestAirport();

        sqlRunner.runAllDDL();
        mainFileLoader.loadAllFiles();
        sqlRunner.runETL();
        sqlRunner.runAfterETL();
        closestAirport.setClosestAirports();
    }
}
