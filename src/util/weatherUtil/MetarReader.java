package util.weatherUtil;

import model.time.Day;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;

/**
 * Created by kenneth on 17/05/2018.
 */
public class MetarReader {
// source for decoding metar used: https://en.wikipedia.org/wiki/METAR


    public static WeatherRecord decodeMetar(String metar) {
        WeatherRecord weatherRecord = new WeatherRecord();

        if (metar.substring(0, 5).equalsIgnoreCase("metar")) {
            // it is a metar and not a special report, meaning that this is a scheduled hourly update
            String[] split = metar.substring(5, metar.length()).split(" ");
            weatherRecord.setAirportCode(new ICAOAirportCode(split[0]));

            String dayTimeString = split[1];
            if (dayTimeString.charAt(dayTimeString.length() - 1) == 'z') {
                //this is the day and time bit
                String dayOfMonth = dayTimeString.substring(0, 2);
                weatherRecord.setDay(new Day(Integer.parseInt(dayOfMonth)));
//                weatherRecord.set
            }



        }

        return weatherRecord;
    }

    public static void main(String[] args) {
        decodeMetar("metarekcssa");
    }
}
