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

        sqlRunner.runAllDDL();

        MainFileLoader.main(new String[1]);

        sqlRunner.runETL();
        sqlRunner.runAfterETL();

        ClosestAirport.main(new String [1]);

    }
}
