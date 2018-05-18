package util.weatherUtil;

import model.geography.Degree;
import model.time.Day;
import model.time.Hour;
import model.time.Minute;
import model.weather.*;

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

            int counter = 0;
            for (String item : split) {
                System.out.println(counter);
                item = item.trim();         // trimming each section
                System.out.println("item: "  + item);
                switch (counter) {
                    case 0: // setting record id/code
                        if (item.length() != 4) {
                            continue;
                        }
                        weatherRecord.setAirportCode(new ICAOAirportCode(split[0]));
                        counter++;
                        break;
                    case 1: // case for time
                        String dayTimeString = item;
                        if (dayTimeString.charAt(dayTimeString.length() - 1) == 'z') {
                            //this is the day and time bit
                            String dayOfMonth = dayTimeString.substring(0, 2);
                            weatherRecord.setDay(new Day(Integer.parseInt(dayOfMonth)));

                            String hour = dayTimeString.substring(2, 4);
                            weatherRecord.setHour(new Hour(Integer.parseInt(hour)));

                            String minute = dayTimeString.substring(4, 6);
                            weatherRecord.setMinute(new Minute(Integer.parseInt(minute)));
                            counter++;
                        }
                        break;
                    case 2: // wind
                        if (item.substring(item.length() - 2, item.length()).equalsIgnoreCase("kt")) {

                            int intWindDirection = Integer.parseInt(item.substring(0, 3));
                            int intWindSpeed = Integer.parseInt(item.substring(3, 5));

                            WindDirection windDirection = new WindDirection(new Degree(intWindDirection));
                            WindSpeed windSpeed = new WindSpeed(intWindSpeed);
                            weatherRecord.setWind(new Wind(windDirection, windSpeed));
                            counter++;
                        }
                        break;
                    case 3: // temperature and dew point:
                        String[] temps = item.split("/");
                        if (temps.length == 2) {
                            // getting the temperature (index 0)
                            DegreeCelcius degreeCelcius;
                            if (temps[0].charAt(0) == 'M') {
                                int temp = Integer.parseInt(temps[0].substring(1, 3));
                                double theTemp = 0 - temp;
                                degreeCelcius = new DegreeCelcius(theTemp);
                            } else {
                                int temp = Integer.parseInt(temps[0]);
                                degreeCelcius = new DegreeCelcius((double) temp);
                            }
                            weatherRecord.setTemperature(degreeCelcius);

                            //getting (dew point)
                            DegreeCelcius dewPoint;
                            if (temps[1].charAt(0) == 'M') {
                                int temp = Integer.parseInt(temps[1].substring(1, 3));
                                double theTemp = 0 - temp;
                                dewPoint = new DegreeCelcius(theTemp);
                            } else {
                                int temp = Integer.parseInt(temps[1]);
                                dewPoint = new DegreeCelcius((double) temp);
                            }
                            weatherRecord.setDewPoint(degreeCelcius);
                            counter++;
                        }
                        break;
                }

            }


        } else {
            throw new RuntimeException("error in reading metar");
        }
        return weatherRecord;
    }

    public static void main(String[] args) {
        WeatherRecord weatherRecord = decodeMetar("METAR	eksn 170950z 01012kt cavok 15/10 q1016");

        System.out.println("airport code:" + weatherRecord.getAirportCode().getICAOCode());
        System.out.println("temperature:" + weatherRecord.getTemperature().getTemperature());
        System.out.println("Wind speed:" + weatherRecord.getWind().getWindSpeed());
        System.out.println("Wind Direction:" + weatherRecord.getWind().getWindDirection());
        System.out.println("Time of measurement:" + weatherRecord.getHour());
        System.out.println("Day of measurement:" + weatherRecord.getDayOfMonth().getDayOfMonth());
        System.out.println("Minute of measurement:" + weatherRecord.getMinute());
    }
}
