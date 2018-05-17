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
        int datalogID = new DatabaseHelper<Integer>().mapSingle(rs -> rs.getInt(1), "Select data_Logger_ID.nextval from dual");
        icgHelper.executeUpdate("INSERT INTO Data_Logger (id, gliderRegNo, flightDate) " +
                "VALUES (?, ?, to_Date(?, \'yy/mm/dd\'))",
                datalogID, logger.getGliderID(), d);

        ArrayList<DataPoint> points = logger.getDatalog();
        String satCover = "";
        String tsmp = "";
        for(int i=0; i<points.size(); i++)
        {
             DataPoint point = points.get(i);
             satCover = "" + point.getSataliteCoverage();
             tsmp =  d + " " + point.getTime().toString();
            icgHelper.executeUpdate("INSERT INTO IGC_Source_Data " +
                            "(id, TIMEOFLOG, LATITUDE, LONGITUDE, SATELITECOVERAGE, PRESSUREALTITUDE, GPSALTITUDE, FLIGTHID) " +
                            "VALUES (IGC_Source_Data_ID.NEXTVAL, to_Timestamp(?, \'YY/MM/DD HH24:MI:SS\'), ?, ?, ?, ?, ?, ?)",
                     tsmp, point.getLatitude().toDatabase(),
                    point.getLongitude().toDatabase(), satCover,
                    point.getPressureAltitude(), point.getGPSAltitude(), datalogID);
        }
    };
}
