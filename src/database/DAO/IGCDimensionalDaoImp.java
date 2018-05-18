package database.DAO;

import model.igc.Flight;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 18/05/2018.
 */
public class IGCDimensionalDaoImp implements IGCDimensionalDao {

    private Connection connection;

    public IGCDimensionalDaoImp() {
        this.connection = DatabaseHelper.getInstance().getConnection();
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> flights = new ArrayList<>();

        return null;
    }
}
