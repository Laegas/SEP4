package database.DAO;

import model.time.Date;
import model.weather.Airport;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLDataException;
import java.sql.SQLException;

public class METARSourceDAOImp implements METARSourceDAO {
    private Connection conn;

    METARSourceDAOImp()
    {
        conn = DatabaseHelper.getInstance().getConnection();
    }

    @Override
    public void insertWeatherRecord(WeatherRecord record) {
        try {
            String ICAO_airport_code = record.getAirportCode().getICAOCode();
            int windSpeed = record.getWind().getWindSpeed().getKnots();
            int windDirection = 0;
            try {
                windDirection = record.getWind().getWindDirection().getDegree().getDegree();
            } catch (Exception e) {
                // ignore
            }
            int windDirectionFrom, windDirectionTo;
            if(record.getVaryingWindDirection() == null) {
                windDirectionTo = -1;
                windDirectionFrom = -1;
            } else {
                windDirectionFrom = record.getVaryingWindDirection().getFrom().getDegree();
                windDirectionTo = record.getVaryingWindDirection().getTo().getDegree();
            }
            double temperature = record.getTemperature().getTemperature();
            double dewPoint = record.getDewPoint().getTemperature();
            Date date = new Date(record.getDayOfMonth().getDayOfMonth(),record.getMonth().getMonthNumber(),record.getYear().getYear());
            int minute = record.getMinute().getMinute();
            int hour = record.getHour().getHour();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO weather_record (id, ICAO_airport_code, " +
                     "wind_direction, wind_speed, wind_direction_from, wind_direction_to" +
                     ", temperature, dew_point, the_date, minute, hour) VALUES (weather_record_id.nextval, ?, ?, ?, ?, ?, ?, ?, to_Date(?, \' yy/mm/dd\'), ?, ? )");
            /*System.out.println(ICAO_airport_code + " " + windSpeed + " " + windDirection + " " + windDirectionFrom +
                    " " + windDirectionTo + " " + temperature + " " + dewPoint + " " + date.toString() + " " + hour +
                    " " + minute);*/
             stmt.setString(1, ICAO_airport_code);
             stmt.setInt(2,windDirection);
             stmt.setInt(3,windSpeed);
             stmt.setInt(4,windDirectionFrom);
            stmt.setInt(5, windDirectionTo);
                stmt.setDouble(6, temperature);
                stmt.setDouble(7, dewPoint);


//            stmt.setDouble(6,19.8);
//            stmt.setDouble(7,25.3);
             stmt.setString(8,date.toString());
             stmt.setInt(9, minute);
            stmt.setInt(10, hour);
            try {

                stmt.execute();
            } catch (SQLDataException e) {
                e.printStackTrace();
                System.out.println(temperature + ", " + dewPoint);
            }finally {

                stmt.close();
            }


             conn.commit();
             } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertAirport(Airport airport) {

        // insert airports into source tables

//        String sql = "";
        String sql = "insert into airport (ICAO_AIRPORT_CODE, LATITUDE, LONGITUDE, COUNTRYNAME, AIRPORTNAME, ALTITUDE, WMO_INDEX) VALUES " +
                "(?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm = DatabaseHelper.getInstance().getConnection().prepareStatement(sql);

            stm.setString(1, airport.getAirport().getICAOCode());
            stm.setString(2, airport.getLatitude().toDBString());
            stm.setString(3,airport.getLongitude().toDBString());
            stm.setString(4, airport.getCountryName().getCountryName());
            stm.setString(5,airport.getAirportName());
            stm.setInt(6,airport.getAltitude().getAltitude());
            stm.setString(7, airport.getWmoIndex().getWMOIndex());

            stm.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
//         insert weather records with a reference to their respective airports

        for (WeatherRecord record : airport.getWeatherRecords()) {
            insertWeatherRecord(record);
        }
    }
}
