package launchable.fileloaders;

import database.DAO.DaoManager;
import database.DAO.IGCDimensionalDaoImp;

/**
 * Created by kenneth on 31/05/2018.
 */
public class ClosestAirport {

    public ClosestAirport() {}

    public void setClosestAirports() {
        DaoManager.IGC_DIMENSIONAL_DAO.setClosestAirportsForAllIGC();
    }

    public static void main(String[] args) {
        ClosestAirport closestAirport = new ClosestAirport();
        closestAirport.setClosestAirports();
    }
}
