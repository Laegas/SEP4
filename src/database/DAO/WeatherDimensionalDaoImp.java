package database.DAO;

import model.geography.CountryName;
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
        List<WeatherRecord> weatherRecords = dao.getWeatherRecord(new Date(11, 5, 2018), new ICAOAirportCode("EKAH"));
        System.out.println(weatherRecords.size());
    }
    @Override
    public List<WeatherRecord> getWeatherRecord(Date date, ICAOAirportCode airportCode) {
        Connection conn = DatabaseHelper.getInstance().getConnection();
        String sql = "select w_date,w_time,wind_direction,wind_speed,WIND_DIRECTION_FROM,WIND_DIRECTION_TO,temperature,dew_point,airport_code from F_WEATHER_RECORD where W_DATE = TO_DATE( ?, 'DD/MM/YYYY') AND AIRPORT_CODE = ?";


//        String sql = "select count(*) from F_WEATHER_RECORD where W_DATE = to_date('11/05/2018', 'DD/MM/YYYY')";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            // build the argument
            String argument = "";
            argument += String.format("%02d", date.getDay().getDayOfMonth());
            argument += "/";
            argument += String.format("%02d", date.getMonth().getMonthNumber());
            argument += "/";
            argument += String.format("%04d", date.getYear().getYear());
            argument += "";

            stm.setString(1, argument);
            stm.setString(2,airportCode.getICAOCode());
            ResultSet rs = stm.executeQuery();

            List<WeatherRecord> records = new ArrayList<>();
            Date tmpDate;
            Time tmpTime;
            Wind tmpWind;
            WindSpeed tmpWindSpeed;
            WindDirection tmpWindDirection;
            DegreeCelcius tmpTemperature;
            DegreeCelcius tempDewPoint;
            ICAOAirportCode temp_airportCode;
            WindDirection tmpWindDirectionFrom;
            WindDirection tmpWindDirectionTo;
            VaryingWindDirection tmpVaryingWindDirection;
            while (rs.next()) {
                tmpDate = new Date(rs.getDate("w_date"));
                tmpWindDirection = new WindDirection(new Degree(rs.getInt("wind_direction")));
                tmpWindSpeed = new WindSpeed(rs.getInt("wind_speed"));
                tmpTemperature = new DegreeCelcius(rs.getDouble("temperature"));
                tempDewPoint = new DegreeCelcius(rs.getDouble("dew_point"));
                temp_airportCode = new ICAOAirportCode(rs.getString("airport_code"));
                tmpWindDirectionFrom = new WindDirection(new Degree(rs.getInt("wind_direction_from")));
                tmpWindDirectionTo = new WindDirection(new Degree(rs.getInt("wind_direction_to")));

                tmpVaryingWindDirection = new VaryingWindDirection(tmpWindDirectionFrom.getDegree(), tmpWindDirectionTo.getDegree());
                tmpWind = new Wind(tmpWindDirection, tmpWindSpeed);

                java.sql.Time sqlTime = rs.getTime("w_time");
                tmpTime = new Time(new Hour(sqlTime.getHours()), new Minute(sqlTime.getMinutes()), new Second(0));

                records.add(new WeatherRecord(temp_airportCode,tmpWind,tmpVaryingWindDirection,tmpTemperature,tempDewPoint,tmpDate.getDay(),tmpDate.getMonth(),tmpDate.getYear(),tmpTime.getHour(),tmpTime.getMinute() ));
            }

            stm.close();

            return records;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public Airport getAirportByDateAndICAOCode(ICAOAirportCode code, Date date) {
        Connection conn = DatabaseHelper.getInstance().getConnection();
        Airport airport = null;
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT ICAO_AIRPORT_CODE, LATITUDE, LONGITUDE, COUNTRYNAME, AIRPORTNAME, ALTITUDE, WMO_INDEX FROM AIRPORT WHERE AIRPORT_CODE = ?");
            stm.setString(1,code.getICAOCode());
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                airport.setWmoIndex(new WMOIndex(rs.getString("WMO_INDEX")));
                airport.setAirportName(rs.getString("AIRPORTNAME"));
                airport.setAirport(code);
                airport.setAltitude(new Altitude(rs.getInt("ALTITUDE")));
                airport.setCountryName(new CountryName(rs.getString("COUNTRYNAME")));
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return airport;
    }

    @Override
    public List<Airport> getAirportsByDate(Date date) {
        return null;
    }
}
