package database.DAO;

import caching.LocalCache;
import model.geography.Latitude;
import model.geography.Longitude;
import model.igc.DataPoint;
import model.igc.Flight;
import model.igc.Glider;
import model.time.Date;
import model.time.Time;
import model.weather.Airport;
import model.weather.ICAOAirportCode;
import org.apache.tools.ant.taskdefs.Local;
import util.geography.GeoCaluclator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 18/05/2018.
 */
public class IGCDimensionalDaoImp implements IGCDimensionalDao {

    private boolean debug = false;
    private Connection connection;

    IGCDimensionalDaoImp() {
        this.connection = DatabaseHelper.getInstance().getConnection();
    }

    @Override
    public List<Flight> getAllFlights() {
        List<Flight> noDataFlights = new ArrayList<>();
        List<Glider> gliders = new ArrayList<>();
        List<Flight> result = new ArrayList<>();


        //getting all gliders
        PreparedStatement stmt = null;
        try {
            String sql = "select GLIDER_ID, surr_key_glider from d_glider";
            stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            Glider tmpGlider;
            boolean a = true;
            dprint(String.valueOf(a));
            while (a = resultSet.next()) {
                dprint(String.valueOf(a));
                dprint(resultSet.getString("glider_id"));
                tmpGlider = new Glider(resultSet.getInt("surr_key_glider"), resultSet.getString("glider_id"));
                gliders.add(tmpGlider);
            }
            dprint("number of gliders: " + gliders.size());

            //getting all flights
            sql = "SELECT surr_key_flight, ID_START_DATE, SURR_KEY_GLIDER FROM D_FLIGHT";

            stmt = connection.prepareStatement(sql);
            resultSet = stmt.executeQuery();
            ResultSet resultDate = null;
            Flight tmpFlight;
            while (resultSet.next()) {
                String sql1 = "SELECT day, month, year from D_DATE where ID_DATE = ?";
                int sDate = resultSet.getInt("ID_START_DATE");
                int surrfKey =  resultSet.getInt("surr_key_flight");
                int gliderID = resultSet.getInt("SURR_KEY_GLIDER");
                stmt = connection.prepareStatement(sql1);
                stmt.setInt(1, sDate);
                resultDate = stmt.executeQuery();
                resultDate.next();
                tmpFlight = new Flight(new Date(Integer.parseInt(resultDate.getString("day")),Integer.parseInt(resultDate.getString("month")), Integer.parseInt(resultDate.getString("year"))),surrfKey,getGliderByID(gliders,gliderID));
                noDataFlights.add(tmpFlight);
            }
            dprint("number of flights: " + noDataFlights.size());


            //getting all data for each flight one flight at a time
            for (Flight flight : noDataFlights) {
                List<DataPoint> dataPoints = getDataPoints(flight.getFlight_id());
                tmpFlight = new Flight(flight.getDate(),dataPoints,flight.getGlider(),flight.getFlight_id());

                result.add(tmpFlight);
            }
            noDataFlights = null;

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    private List<DataPoint> getDataPoints(int flightNumber) {

        List<DataPoint> result = new ArrayList<>();
        String sql = "SELECT surr_key_log, surr_key_flight, ID_time, lat_north, long_east, press_altitude, gps_altitude, gps_ok, CLOSEST_AIRPORT FROM F_IGC_LOG where SURR_KEY_FLIGHT = ?";
        PreparedStatement stmt = null;
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1,flightNumber);
            ResultSet resultSet = stmt.executeQuery();
            DataPoint tmpDataPoint;
            Time tmpTime;
            Longitude tmpLongitude;
            Latitude tmpLatitude;
            int pressureAltitude;
            int gpsAltitude;
            int flight_id;
            int glider_id;
            char satelliteCoverage;
            int tempId_time;
            while (resultSet.next()) {
                int timeKey = resultSet.getInt("ID_time");
                TimeDimensionalDAO timeDao = DaoManager.TIME_DAO;
                tmpTime = timeDao.getTimeByID(timeKey);
                String longitude = resultSet.getString("long_east");
                dprint(longitude);
                tmpLongitude = new Longitude(Integer.parseInt(longitude.substring(0, 3)), Integer.parseInt(longitude.substring(3, 5)), Integer.parseInt(longitude.substring(5, 8)));
                String latitude = resultSet.getString("lat_north");
                tmpLatitude = new Latitude(Integer.parseInt(latitude.substring(0, 2)), Integer.parseInt(latitude.substring(2, 4)), Integer.parseInt(latitude.substring(4, 7)));
                pressureAltitude = resultSet.getInt("press_altitude");
                gpsAltitude = resultSet.getInt("gps_altitude");
                flight_id = resultSet.getInt("surr_key_flight");
                satelliteCoverage = resultSet.getString("gps_ok").charAt(0);

                tempId_time = timeKey;
                tmpDataPoint = new DataPoint(tmpTime, tmpLongitude, tmpLatitude, satelliteCoverage, pressureAltitude, gpsAltitude, flight_id, tempId_time);
                String closest_airport = resultSet.getString("closest_airport");
                if (closest_airport != null) {
                    // use the local cache with the weather dao (get map) !!! TODO
                    ICAOAirportCode tmpCode = tmpCode = LocalCache.INSTANCE.airportICAOCodeBySurrKey(Integer.parseInt(closest_airport));
                    tmpDataPoint.setClosest_airport(new ICAOAirportCode(tmpCode.getICAOCode()));
                }
                result.add(tmpDataPoint);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private Glider getGliderByID(List<Glider> gliders, int glider_id) {
        for (Glider glider : gliders) {
            if (glider.getGlider_id() == glider_id) {
                return glider;
            }
        }
        throw new RuntimeException("something messed up");
    }

    private void dprint(String string) {
        if (debug) {
            System.out.println(string);
        }
    }

    public void setClosestAirportsForAllIGC() {
        List<Airport> airports = DaoManager.WEATHER_DIMENSIONAL_DAO.getAirportsByDate(new Date(1, 1, 1));
        List<Flight> flights = DaoManager.IGC_DIMENSIONAL_DAO.getAllFlights();

        for (Flight flight : flights) {

            double shortestDistance = Double.MAX_VALUE;
            Airport resultPointer = null;
            double tempDist = 0;

            for (DataPoint dataPoint : flight.getDatalog()) {
                shortestDistance = Double.MAX_VALUE;
                resultPointer = null;
                //                find the closest airport and set it
                for (Airport airport : airports) {
                    tempDist = GeoCaluclator.getGeoDistance(dataPoint.getLatitude(),dataPoint.getLongitude(), airport.getLatitude(), airport.getLongitude());
                    if (tempDist < shortestDistance) {
                        resultPointer = airport;
                        shortestDistance = tempDist;
                    }
                }
                int tmpSurr = LocalCache.INSTANCE.airportSurrKeyByICAOCode(resultPointer.getAirport());
                setClosestAirport(dataPoint, tmpSurr);
            }

        }
    }


    private void setClosestAirport(DataPoint dataPoint, int surr_key_airport) {


        String sql = "update F_IGC_LOG set CLOSEST_AIRPORT = ? WHERE SURR_KEY_FLIGHT = ? AND ID_TIME= ?";
        try {
            PreparedStatement stm = DatabaseHelper.getInstance().getConnection().prepareStatement(sql);

            stm.setInt(1,surr_key_airport);
            stm.setInt(2, dataPoint.getFlight_id());
            stm.setInt(3, dataPoint.getTime_id());

            int rowsAffected = stm.executeUpdate();

            this.connection.commit();
            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
