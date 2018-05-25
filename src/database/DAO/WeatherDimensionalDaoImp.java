package database.DAO;

import model.geography.Degree;
import model.time.*;
import model.time.Date;
import model.time.Time;
import model.weather.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kenneth on 24/05/2018.
 */
public class WeatherDimensionalDaoImp implements WeatherDimensionalDao {

    public static void main(String[] args) {
        WeatherDimensionalDao dao = new WeatherDimensionalDaoImp();
        List<WeatherRecord> weatherRecords = dao.getWeatherRecord(new Date(11, 5, 2018));
        System.out.println(weatherRecords.size());
    }
    @Override
    public List<WeatherRecord> getWeatherRecord(Date date) {
        Connection conn = DatabaseHelper.getInstance().getConnection();
        String sql = "select w_date,w_time,wind_direction,wind_speed,WIND_DIRECTION_FROM,WIND_DIRECTION_TO,temperature,dew_point,airport_code from f_weather_record where w_date = TO_DATE(?/?/?, 'dd/mm/yyyy')";


        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setString(1,String.format("%02d", date.getDay().getDayOfMonth()));
            stm.setString(2, String.format("%02d", date.getMonth().getMonthNumber()));
            stm.setString(3, String.format("%04d", date.getYear().getYear()));


            ResultSet rs = stm.executeQuery();

            List<WeatherRecord> records = new ArrayList<>();
            Date tmpDate;
            Time tmpTime;
            Wind tmpWind;
            WindSpeed tmpWindSpeed;
            WindDirection tmpWindDirection;
            DegreeCelcius tmpTemperature;
            DegreeCelcius tempDewPoint;
            ICAOAirportCode airportCode;
            WindDirection tmpWindDirectionFrom;
            WindDirection tmpWindDirectionTo;
            VaryingWindDirection tmpVaryingWindDirection;
            while (rs.next()) {
                tmpDate = new Date(rs.getDate("w_date"));
                tmpWindDirection = new WindDirection(new Degree(rs.getInt("wind_direction")));
                tmpWindSpeed = new WindSpeed(rs.getInt("wind_speed"));
                tmpTemperature = new DegreeCelcius(rs.getDouble("temperature"));
                tempDewPoint = new DegreeCelcius(rs.getDouble("dew_point"));
                airportCode = new ICAOAirportCode(rs.getString("airport_code"));
                tmpWindDirectionFrom = new WindDirection(new Degree(rs.getInt("wind_direction_from")));
                tmpWindDirectionTo = new WindDirection(new Degree(rs.getInt("wind_direction_to")));

                tmpVaryingWindDirection = new VaryingWindDirection(tmpWindDirectionFrom.getDegree(), tmpWindDirectionTo.getDegree());
                tmpWind = new Wind(tmpWindDirection, tmpWindSpeed);

                java.sql.Time sqlTime = rs.getTime("w_time");
                tmpTime = new Time(new Hour(sqlTime.getHours()), new Minute(sqlTime.getMinutes()), new Second(0));

                records.add(new WeatherRecord(airportCode,tmpWind,tmpVaryingWindDirection,tmpTemperature,tempDewPoint,tmpDate.getDay(),tmpDate.getMonth(),tmpDate.getYear(),tmpTime.getHour(),tmpTime.getMinute() ));
            }


            return records;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
