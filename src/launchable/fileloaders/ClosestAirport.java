package launchable.fileloaders;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDaoImp;

/**
 * Created by kenneth on 31/05/2018.
 */
public class ClosestAirport {
    public static void main(String[] args) {
        DaoManager.IGC_DIMENSIONAL_DAO.setClosestAirportsForAllIGC();
    }
}
