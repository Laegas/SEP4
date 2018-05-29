package database.DAO;

import model.geography.CountryName;
import model.geography.Degree;
import model.geography.Latitude;
import model.geography.Longitude;
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
                String latitude = rs.getString("LATITUDE");
                airport.setLatitude(new Latitude(Integer.parseInt(latitude.substring(0, 2)), Integer.parseInt(latitude.substring(2, 4)), Integer.parseInt(latitude.substring(4, 7))));
                String longitude = rs.getString("LONGITUDE");
                airport.setLongitude(new Longitude(Integer.parseInt(longitude.substring(0, 3)), Integer.parseInt(longitude.substring(3, 5)), Integer.parseInt(longitude.substring(5, 8))));
            }
            stm.close();
            airport.setWeatherRecords((WeatherRecord[]) getWeatherRecord(date,code).toArray());
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return airport;
    }

    @Override
    public List<Airport> getAirportsByDate(Date date) {
        Connection conn = DatabaseHelper.getInstance().getConnection();

        List<Airport> airports = new ArrayList<>();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT ICAO_AIRPORT_CODE, LATITUDE, LONGITUDE, COUNTRYNAME, AIRPORTNAME, ALTITUDE, WMO_INDEX FROM AIRPORT");
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                Airport airport =new Airport();
                airport.setWmoIndex(new WMOIndex(rs.getString("WMO_INDEX")));
                airport.setAirportName(rs.getString("AIRPORTNAME"));
                ICAOAirportCode airport_code =new ICAOAirportCode(rs.getString("ICAO_AIRPORT_CODE"));
                airport.setAirport(airport_code);
                airport.setAltitude(new Altitude(rs.getInt("ALTITUDE")));
                airport.setCountryName(new CountryName(rs.getString("COUNTRYNAME")));
                String latitude = rs.getString("LATITUDE");
                airport.setLatitude(new Latitude(Integer.parseInt(latitude.substring(0, 2)), Integer.parseInt(latitude.substring(2, 4)), Integer.parseInt(latitude.substring(4, 7))));
                String longitude = rs.getString("LONGITUDE");
                airport.setLongitude(new Longitude(Integer.parseInt(longitude.substring(0, 3)), Integer.parseInt(longitude.substring(3, 5)), Integer.parseInt(longitude.substring(5, 8))));
                airport.setWeatherRecords((WeatherRecord[]) getWeatherRecord(date,airport_code).toArray());
                airports.add(airport);
            }
            stm.close();

        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return airports;

    }
}
