package database.DAO;

import model.igc.DataLogger;
import model.igc.DataPoint;
import model.geography.Longitude;
import model.geography.Latitude;

import java.util.ArrayList;

public class IGCdao implements IGCDataDAO {
    private DatabaseHelper<IGCDataDAO> icgHelper;

    public IGCdao()
    {
        icgHelper = new DatabaseHelper<>();
    }

    //data points are inserted within this method, immediately after inserting the data logger
    public void insertDataLogger(DataLogger logger)
    {
        String d = logger.getDate().toString();
        icgHelper.executeUpdate("INSERT INTO Data_Logger (id, gliderRegNo, flightDate) " +
                "VALUES (data_Logger_ID.NEXTVAL, ?, to_Date(?, \'yyyy/mm/dd\'))",
                logger.getGliderID(), d);

        ArrayList<DataPoint> points = logger.getDatalog();

        for(int i=0; i<points.size(); i++)
        {
             DataPoint point = points.get(i);
             String tsmp = d + " " + point.getTime().toString();
            icgHelper.executeUpdate("INSERT INTO IGC_Source_Data " +
                            "(id, timeOfLog, latitude, longitude, " +
                            "SATELITECOVERAGE, pressureAltitude, GPSAltitude, FLIGTHID) " +
                            "VALUES (IGC_Source_Data_ID.nextVal, to_Timestamp(?, \'YYYY/MM/DD HH:MI:SS\'), ?, ?, ?, ?, ?, data_Logger_ID.CURRVAL)",
                     tsmp, point.getLatitude().toDatabase(),
                    point.getLongitude().toDatabase(), point.getSataliteCoverage(),
                    point.getPressureAltitude(), point.getGPSAltitude());
        }
    };
}
