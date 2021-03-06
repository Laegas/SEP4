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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kenneth on 24/05/2018.
 * this class makes is so that only weather data from the airport with ICAO code EKSN will be used as the two other airports that are referenced by our igc data dont have any weather data.
 */

public class WeatherDimensionalDaoImp implements WeatherDimensionalDao {

    private static boolean USING_ONLY_WEATHER_FROM_DEAFULT_AIRPORT = true;
    private static final ICAOAirportCode DEFAULT_AIRPORT_CODE = new ICAOAirportCode("EKAH");



    @Override
    public List<WeatherRecord> getWeatherRecord(Date date, ICAOAirportCode airportCode) {
        Connection conn = DatabaseHelper.getInstance().getConnection();
        String sql = "select w.id_date,w.id_time,w.wind_direction,w.wind_speed,w.WIND_DIRECTION_FROM,w.WIND_DIRECTION_TO,w.temperature,w.dew_point,w.ICAO_airport_code from F_WEATHER_RECORD w join D_DATE d on (d.ID_DATE = w.ID_DATE) where d.DAY=? AND d.MONTH=? AND d.YEAR=? AND w.ICAO_AIRPORT_CODE = ?";


//        String sql = "select count(*) from F_WEATHER_RECORD where W_DATE = to_date('11/05/2018', 'DD/MM/YYYY')";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            // build the argument
            String year = date.getYear().getYear()+"";
            String month = date.getMonth().getMonthNumber()+"";
            String day = date.getDay().getDayOfMonth()+"";

            stm.setString(1, day);
            stm.setString(2,month);
            stm.setString(3, year);
            if (USING_ONLY_WEATHER_FROM_DEAFULT_AIRPORT) {
                stm.setString(4,DEFAULT_AIRPORT_CODE.getICAOCode());
            } else {
                stm.setString(4,airportCode.getICAOCode());
            }
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
                int dateKey = rs.getInt("id_date");
                TimeDimensionalDAO timeDao = new TimeDimensionalDAOImp();

                tmpDate = timeDao.getDateByID(dateKey);
                tmpWindDirection = new WindDirection(new Degree(rs.getInt("wind_direction")));
                tmpWindSpeed = new WindSpeed(rs.getInt("wind_speed"));
                tmpTemperature = new DegreeCelcius(rs.getDouble("temperature"));
                tempDewPoint = new DegreeCelcius(rs.getDouble("dew_point"));
                temp_airportCode = new ICAOAirportCode(rs.getString("icao_airport_code"));
                tmpWindDirectionFrom = new WindDirection(new Degree(rs.getInt("wind_direction_from")));
                tmpWindDirectionTo = new WindDirection(new Degree(rs.getInt("wind_direction_to")));

                tmpVaryingWindDirection = new VaryingWindDirection(tmpWindDirectionFrom.getDegree(), tmpWindDirectionTo.getDegree());
                tmpWind = new Wind(tmpWindDirection, tmpWindSpeed);

                tmpTime  = timeDao.getTimeByID(rs.getInt("ID_TIME"));

                records.add(new WeatherRecord(temp_airportCode,tmpWind,tmpVaryingWindDirection,tmpTemperature,tempDewPoint,tmpDate.getDay(),tmpDate.getMonth(),tmpDate.getYear(),tmpTime.getHour(),tmpTime.getMinute() ));
            }

            stm.close();

            return records;

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return new ArrayList<>();
    }

    @Override
    public Airport getAirportByDateAndICAOCode(ICAOAirportCode code, Date date) {
        Connection conn = DatabaseHelper.getInstance().getConnection();
        Airport result = new Airport();
        try{
            PreparedStatement stm = conn.prepareStatement("SELECT ICAO_AIRPORT_CODE, airport_LATITUDE, airport_LONGITUDE, COUNTRY_NAME, AIRPORT_NAME, ALTITUDE, WMO_INDEX FROM d_AIRPORT WHERE ICAO_AIRPORT_CODE = ?");
            if (USING_ONLY_WEATHER_FROM_DEAFULT_AIRPORT) {
                stm.setString(1,DEFAULT_AIRPORT_CODE.getICAOCode());
            } else {
                stm.setString(1,code.getICAOCode());
            }
            ResultSet rs = stm.executeQuery();
            while(rs.next())
            {
                result.setWmoIndex(new WMOIndex(rs.getString("WMO_INDEX")));
                result.setAirportName(rs.getString("AIRPORT_NAME"));
                if (USING_ONLY_WEATHER_FROM_DEAFULT_AIRPORT) {
                    result.setAirport(DEFAULT_AIRPORT_CODE);
                } else {
                    result.setAirport(code);
                }
                result.setAltitude(new Altitude(rs.getInt("ALTITUDE")));
                result.setCountryName(new CountryName(rs.getString("COUNTRY_NAME")));
                String latitude = rs.getString("airport_LATITUDE");
                result.setLatitude(new Latitude(Integer.parseInt(latitude.substring(0, 2)), Integer.parseInt(latitude.substring(2, 4)), Integer.parseInt(latitude.substring(4, 7))));
                String longitude = rs.getString("airport_LONGITUDE");
                result.setLongitude(new Longitude(Integer.parseInt(longitude.substring(0, 3)), Integer.parseInt(longitude.substring(3, 5)), Integer.parseInt(longitude.substring(5, 8))));
            }
            stm.close();
            List<WeatherRecord> tmpweatherRecords = getWeatherRecord(date, code);
            if (USING_ONLY_WEATHER_FROM_DEAFULT_AIRPORT) {
                tmpweatherRecords = getWeatherRecord(date, DEFAULT_AIRPORT_CODE);
            } else {
                tmpweatherRecords = getWeatherRecord(date, code);
            }
            result.setWeatherRecords(tmpweatherRecords.toArray(new WeatherRecord[tmpweatherRecords.size()]));
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return result;
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
                airport.setWeatherRecords( getWeatherRecord(date,airport_code));
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

    @Override
    public Map<String, Integer> airportSurrKeyByICAOCode() {
        Map<String, Integer> result = new HashMap<>();

        String sql = "select surr_key_airport, ICAO_AIRPORT_CODE from D_AIRPORT";

        try {
            PreparedStatement stm = DatabaseHelper.getInstance().getConnection().prepareCall(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                result.put( rs.getString("ICAO_AIRPORT_CODE"), Integer.valueOf(rs.getInt("surr_key_airport")));
            }

            stm.close();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
        @Override
    public Map<Integer, String> airportICAOCodeBySurrKey() {
        Map< Integer, String> result = new HashMap<>();

        String sql = "select surr_key_airport, ICAO_AIRPORT_CODE from D_AIRPORT";

        try {
            PreparedStatement stm = DatabaseHelper.getInstance().getConnection().prepareCall(sql);

            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                result.put(Integer.valueOf(rs.getInt("surr_key_airport")), rs.getString("ICAO_AIRPORT_CODE"));
            }
            stm.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return null;
    }
}
