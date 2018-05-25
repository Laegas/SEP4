package database.DAO;

import model.geography.Degree;
import model.time.*;
import model.weather.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String sql = "select the_date,hour,wind_direction,wind_speed,temperature,dew_point,airport_code, minute, wind_direction_from,WIND_DIRECTION_TO from f_weather_record where the_date = TO_DATE(?/?/?, DD/MM/YYYY)";


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
            int hour;
            int minute;
            while (rs.next()) {
                tmpDate = new Date(rs.getDate(1));
                hour = rs.getInt(2);
                tmpWindDirection = new WindDirection(new Degree(rs.getInt(3)));
                tmpWindSpeed = new WindSpeed(rs.getInt(4));
                tmpTemperature = new DegreeCelcius(rs.getDouble(5));
                tempDewPoint = new DegreeCelcius(rs.getDouble(6));
                airportCode = new ICAOAirportCode(rs.getString(7));
                minute = rs.getInt(8);
                tmpWindDirectionFrom = new WindDirection(new Degree(rs.getInt(9)));
                tmpWindDirectionTo = new WindDirection(new Degree(rs.getInt(10)));

               // tmpVaryingWindDirection = new VaryingWindDirection(tmpWindDirectionFrom.getDegree(), tmpWindDirectionTo.getDegree());
                tmpWind = new Wind(tmpWindDirection, tmpWindSpeed);
                tmpTime = new Time(new Hour(hour), new Minute(minute), new Second(0));
                System.out.println("awsome minute :" +  minute);

               // records.add(new WeatherRecord(airportCode,tmpWind,tmpVaryingWindDirection,tmpTemperature,tempDewPoint,tmpDate.getDay(),tmpDate.getMonth(),tmpDate.getYear(),tmpTime.getHour(),tmpTime.getMinute() ));
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
