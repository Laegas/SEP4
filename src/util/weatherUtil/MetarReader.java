package util.weatherUtil;

import model.geography.Degree;
import model.time.*;
import model.weather.*;

public class MetarReader {
// source for decoding metar used: http://pykl3radar.com/library/afpam11-238.pdf
    public static WeatherRecord decodeMetar(String metar) throws METARException {
        WeatherRecord weatherRecord = new WeatherRecord();

        if (metar.substring(13, 18).equalsIgnoreCase("metar")) {
            String[] split = metar.split(" ");
            try {
            if(split[3].equalsIgnoreCase("nil=") || split[4].equalsIgnoreCase("nil=")) {
                throw new METARException("NIL Record");
            } } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(metar);
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

//            System.out.println(split[0]);
            weatherRecord.setYear(new Year(Integer.parseInt(split[0].substring(0, 4))));
            weatherRecord.setMonth(new Month(Integer.parseInt(split[0].substring(4, 6))));
            weatherRecord.setDay(new Day(Integer.parseInt(split[0].substring(6,8))));
            weatherRecord.setHour(new Hour(Integer.parseInt(split[0].substring(8,10))));
            weatherRecord.setMinute(new Minute(Integer.parseInt(split[0].substring(10,12))));

            if(split[2].length() != 4)
                throw new METARException("Invalid ICAO length. Expected 4. Got " + split[2].length());
            weatherRecord.setAirportCode(new ICAOAirportCode(split[2]));

            int padding = 0;
            if(split[4].equalsIgnoreCase("auto"))
                padding++;

            if(!split[4+padding].matches("([0-9]{3}|VRB)[0-9]{2}KT")) {
                throw new METARException("Invalid wind direction and wind speed. Expected format 00000KT or VRB00KT. " +
                        "Got " + split[4+padding]);
            }

            if(split[4+padding].substring(0,3).equalsIgnoreCase("VRB")) {
                weatherRecord.setWind(new Wind(new WindDirection(), new WindSpeed(Integer.parseInt(split[4+padding].substring(3,5)))));
            } else {
                weatherRecord.setWind(new Wind(new WindDirection(new Degree(Integer.parseInt(split[4 + padding].substring(0, 3)))), new WindSpeed(Integer.parseInt(split[4+padding].substring(3,5)))));
            }

            if(split[5+padding].matches("[0-9]{3}V[0-9]{3}")) {
                weatherRecord.setVaryingWindDirection(new VaryingWindDirection(new Degree(Integer.parseInt
                        (split[5+padding].substring(0,3))),
                        new Degree(Integer.parseInt(split[5+padding].substring(4,7)))));
                padding++;
            }

            if(!split[7+padding].matches("[0-9]{2}/M?[0-9]{2}")) {
                throw new METARException("Invalid temperature and dew point. Expected format 00/00 or 00/M00. Got " +
                        split[7+padding]);
            }

            weatherRecord.setTemperature(new DegreeCelcius(Double.parseDouble(split[7+padding].substring(0,2))));
            int p = 0;
            if(split[7+padding].indexOf('M') > 0)
                p = 1;
            weatherRecord.setDewPoint(new DegreeCelcius(Double.parseDouble(split[7+padding].substring(3+p,5+p))));
        } else {
            throw new METARException("Not a METAR record");
        }
        return weatherRecord;
    }
}
