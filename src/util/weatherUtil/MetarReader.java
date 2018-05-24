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


    public static WeatherRecord decodeMetar(String metar) throws NotMETARException {
        // METAR	eksn 170950z 01012kt cavok 15/10 q1016
        /*

					Year year = new Year(Integer.parseInt(strings[0].substring(0, 4)));
					Month month = new Month(Integer.parseInt(strings[0].substring(4, 6)));
					Day day = new Day(Integer.parseInt(strings[0].substring(6,8)));
					Hour hour = new Hour(Integer.parseInt(strings[0].substring(8,10)));
					Minute minute = new Minute(Integer.parseInt(strings[0].substring(10,12)));
					ICAOAirportCode airportCode = new ICAOAirportCode(strings[2]);
					int padding = 0;
					if(l.substring(32,36).equals("AUTO"))
						padding += 5;


					System.out.println(l.substring(32+padding, 36+padding));
				*/
        WeatherRecord weatherRecord = new WeatherRecord();

        if (metar.substring(13, 18).equalsIgnoreCase("metar")) {
            String[] split = metar.split(" ");
            if(!split[4].equalsIgnoreCase("nil=")) {
                for (String s : split) {
                    System.out.print("." + s + ".");
                }
                System.out.println();
            }

            int counter = 0;
            for (String item : split) {
                switch (counter++) {
                    case 0: // setting record id/code
                        if (item.length() != 4) {
                            continue;
                        }
                        weatherRecord.setAirportCode(new ICAOAirportCode(split[0]));
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
                        }
                        break;
                }
            }
        } else {
            throw new NotMETARException("Not a METAR record");
        }

        return weatherRecord;
    }
}
