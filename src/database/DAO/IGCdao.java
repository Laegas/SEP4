package database.DAO;

import model.igc.DataLogger;
import model.igc.DataPoint;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
//Adding this to retry push
public class IGCdao implements IGCDataDAO {

    private Connection conn;

    public IGCdao()
    {
        conn = DatabaseHelper.getInstance().getConnection();
    }

    //data points are inserted within this method, immediately after inserting the data logger
    public void insertDataLogger(DataLogger logger)
    {
        try {
            String date = logger.getDate().toString();
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO Data_Logger (id, glider_RegNo, flight_Date) "
                    + "VALUES (data_Logger_ID.nextval, ?, to_Date(?, \'yy/mm/dd\'))");
            stmt.setString(1, logger.getGliderID());
            stmt.setString(2, date);
            stmt.execute();
            conn.commit();

            ArrayList<DataPoint> points = logger.getDatalog();
            String tsmp = "";
            for (DataPoint point : points) {
                tsmp = date + " " + point.getTime().toString();
                stmt = conn.prepareStatement("INSERT INTO IGC_Source_Data (id, time_Of_Log, LATITUDE, LONGITUDE," +
                        " satelite_Coverage, pressure_Altitude, GPS_Altitude, fligth_ID) " +
                        "VALUES (IGC_Source_Data_ID.NEXTVAL, to_Timestamp(?, \'YY/MM/DD HH24:MI:SS\'), ?, ?, ?, ?, ?," +
                        " data_Logger_ID.currval)");
                stmt.setString(1, tsmp);
                stmt.setString(2, point.getLatitude().toDatabase());
                stmt.setString(3, point.getLongitude().toDatabase());
                stmt.setString(4, point.getSataliteCoverage() + "");
                stmt.setInt(5, point.getPressureAltitude());
                stmt.setInt(6, point.getGPSAltitude());
                stmt.execute();
                conn.commit();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
