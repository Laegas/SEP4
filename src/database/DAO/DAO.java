package database.DAO;

import model.DataLogger;
import model.DataPoint;

import java.util.ArrayList;

public class DAO implements ICGDataDAO {
    private DatabaseHelper<ICGDataDAO> icgHelper;

    public DAO(){
        icgHelper = new DatabaseHelper<>();
    }

    //data points are inserted within this method, immediately after inserting the data logger
    public void insertDataLogger(DataLogger logger)
    {
        icgHelper.executeUpdate("INSERT INTO Data_Logger (id, gliderRegNo, flightDate) " +
                "VALUES (data_Logger_ID.NEXTVAL, ?, ?)",
                logger.getGliderID(), logger.getDate());

        ArrayList<DataPoint> points = logger.getDatalog();
        for(int i=0; i<points.size(); i++)
        {
             DataPoint point = points.get(i);
            icgHelper.executeUpdate("INSERT INTO IGC_Source_Data " +
                            "(id, timeOfLog, latitude, longitude, " +
                            "satelliteCoverage, pressureAltitude, GPSAltitude, flightID) " +
                            "VALUES (IGC_Source_Data_ID.nextVal, ?, ?, ?, ?, ?, ?, data_Logger_ID.CURRVAL)",
                     point.getTime(), point.getLatitude(),
                    point.getLongitude(), point.getSataliteCoverage(),
                    point.getPressureAltitude(), point.getGPSAltitude());
        }
    };
}
