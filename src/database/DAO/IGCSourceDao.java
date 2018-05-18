package database.DAO;

import model.igc.Flight;
import model.igc.DataPoint;
import model.time.Date;
import model.weather.WeatherRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
//Adding this to retry push
public class IGCSourceDao implements IGCDataDAO {

    private Connection conn;

    public IGCSourceDao()
    {
        conn = DatabaseHelper.getInstance().getConnection();
    }

    //data points are inserted within this method, immediately after inserting the data logger
    public void insertDataLogger(Flight logger)
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
                        " satelite_Coverage, pressure_Altitude, GPS_Altitude, flight_ID) " +
                        "VALUES (IGC_Source_Data_ID.NEXTVAL, to_Timestamp(?, \'YY/MM/DD HH24:MI:SS\'), ?, ?, ?, ?, ?," +
                        " data_Logger_ID.currval)");
                stmt.setString(1, tsmp);
                stmt.setString(2, point.getLatitude().toDatabase());
                stmt.setString(3, point.getLongitude().toDatabase());
                stmt.setString(4, point.getSataliteCoverage() + "");
                stmt.setInt(5, point.getPressureAltitude());
                stmt.setInt(6, point.getGPSAltitude());
                stmt.execute();
                stmt.close();
                conn.commit();

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void insertWeatherRecord(WeatherRecord record) {
        try {

            String ICAO_airport_code = record.getAirportCode().getICAOCode();
            int windSpeed = record.getWind().getWindSpeed().getKonts();
            int winddirection = record.getWind().getWindDirection().getDegree().getDegree();
            int windDirectionFrom = record.getVaryingWindDirection().getFrom().getDegree();
            int windDirectionTo = record.getVaryingWindDirection().getTo().getDegree();
            double temperature = record.getTemperature().getTemperature();
            double dewPoint = record.getDewPoint().getTemperature();
            Date date = new Date(record.getDayOfMonth().getDayOfMonth(),record.getMonth().getMonthNumber(),record.getYear().getYear());
            int minute = record.getMinute().getMinute();

            PreparedStatement stmt = conn.prepareStatement("INSERT INTO weather_record (id, ICAO_airport_code, wind_direction, wind_speed, wind_direction_from, wind_direction_to" +
                    ", temperature, dew_point, the_date, minute) VALUES (weather_record_id.nextval, ?, ?, ?, ?, ?, ?, ?, to_Date(?, \' yy/mm/dd\'), ? )");

            stmt.setString(1, ICAO_airport_code);
            stmt.setInt(2,windSpeed);
            stmt.setInt(3,winddirection);
            stmt.setInt(4,windDirectionFrom);
            stmt.setInt(5,windDirectionTo);
            stmt.setDouble(6,temperature);
            stmt.setDouble(7,dewPoint);
            stmt.setString(8,date.toString());
            stmt.setInt(9,minute);
            stmt.execute();
            stmt.close();
            conn.commit();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}