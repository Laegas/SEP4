package database.DAO;

import model.time.Date;
import model.weather.WeatherRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class METARSourceDAOImp implements METARSourceDAO {
    private Connection conn;

    public METARSourceDAOImp()
    {
        conn = DatabaseHelper.getInstance().getConnection();
    }




    public void insertWeatherRecord(WeatherRecord record) {
        try {

            String ICAO_airport_code = record.getAirportCode().getICAOCode();
            int windSpeed = record.getWind().getWindSpeed().getKonts();
            int windDirection = record.getWind().getWindDirection().getDegree().getDegree();
            int windDirectionFrom = record.getVaryingWindDirection().getFrom().getDegree();
            int windDirectionTo = record.getVaryingWindDirection().getTo().getDegree();
            double temperature = record.getTemperature().getTemperature();
            double dewPoint = record.getDewPoint().getTemperature();
            Date date = new Date(record.getDayOfMonth().getDayOfMonth(),record.getMonth().getMonthNumber(),record.getYear().getYear());
            int minute = record.getMinute().getMinute();
            int hour = record.getHour().getHour();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO weather_record (id, ICAO_airport_code, wind_direction, wind_speed, wind_direction_from, wind_direction_to" +
                     ", temperature, dew_point, the_date, minute, hour) VALUES (weather_record_id.nextval, ?, ?, ?, ?, ?, ?, ?, to_Date(?, \' yy/mm/dd\'), ?, ? )");

             stmt.setString(1, ICAO_airport_code);
             stmt.setInt(2,windSpeed);
             stmt.setInt(3,windDirection);
             stmt.setInt(4,windDirectionFrom);
             stmt.setInt(5,windDirectionTo);
             stmt.setDouble(6,temperature);
             stmt.setDouble(7,dewPoint);
             stmt.setString(8,date.toString());
             stmt.setInt(9, hour);
             stmt.setInt(10,minute);
             stmt.execute();
             stmt.close();
             conn.commit();
             } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
