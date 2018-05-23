package fileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import model.igc.Flight;
import model.igc.DataPoint;
import model.geography.Latitude;
import model.geography.Longitude;
import model.igc.Glider;
import model.time.*;
import model.weather.ICAOAirportCode;
import model.weather.WeatherRecord;

public class FileDecoder {
	private Scanner sc;
	private File file;

	public FileDecoder(String filename)
	{
		this.file= new File(filename);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public WeatherRecord readMETARFile() {

		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc.hasNextLine()) {
			String l = sc.nextLine();
			if(l.startsWith("1") || l.startsWith("2")) {
				if (l.substring(13, 18).equals("METAR")) {
					Year year = new Year(Integer.parseInt(l.substring(0, 4)));
					Month month = new Month(Integer.parseInt(l.substring(4, 6)));
					Day day = new Day(Integer.parseInt(l.substring(6,8)));
					Hour hour = new Hour(Integer.parseInt(l.substring(8,10)));
					Minute minute = new Minute(Integer.parseInt(l.substring(10,12)));

					ICAOAirportCode airportCode = new ICAOAirportCode(l.substring(19, 23));


					System.out.println(l.substring(19, 23));
				}
			}
		}

		return null;/*new WeatherRecord(airportCode, wind, varyingWindDirection, temperature, dewPoint, day, month,
		year,
				hour, minute);*/
	}
	
	public Flight readIGCFile()
	{
		ArrayList<DataPoint> points = new ArrayList<>();
		Flight logger = null;
		
		
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while (sc.hasNextLine()) {
			String container = sc.nextLine();
			if (container.startsWith("B"))// DataPoint instance
			{
				String hour = container.substring(1,3);
				String minute = container.substring(3,5);
				String second = container.substring(5,7);
				Time time = new Time(Integer.parseInt(hour),Integer.parseInt(minute),Integer.parseInt(second));
				
				int latitudeDegree = Integer.parseInt(container.substring(7,9));
				int latitudeMinute = Integer.parseInt(container.substring(9,11));
				int latitudeDecimals = Integer.parseInt(container.substring(11,14));
				double latitudeSeconds =  (double)latitudeDecimals*60/1000;
				Latitude latitude = new Latitude(latitudeDegree,latitudeMinute,latitudeSeconds);

				int longitudeDegree = Integer.parseInt(container.substring(15,18));
				int longitudeMinute = Integer.parseInt(container.substring(18,20));
				int longitudeDecimals = Integer.parseInt(container.substring(20,23));
				double longitudeSeconds = (double) longitudeDecimals*60/1000;
                Longitude longitude = new Longitude(longitudeDegree,longitudeMinute,longitudeSeconds);
				
				char satelliteCoverage = container.toCharArray()[24];
				int pressureAltitude = Integer.parseInt(container.substring(25,30));
				int GPSAltitude = Integer.parseInt(container.substring(30,35));
				
				points.add(new DataPoint( time, longitude, latitude, satelliteCoverage, pressureAltitude, GPSAltitude));
			}
			else if(container.startsWith("HFGIDGLIDERID")) // Glider ID
			{
				logger.setGlider(new Glider(container.substring(14)));
			}
			else if (container.startsWith("HFDTE"))//  Date information
			{
				logger = new Flight(new Date(Integer.parseInt(container.substring(5, 7)),Integer.parseInt(container.substring(7, 9)),Integer.parseInt(container.substring(9, 11))));
			}
			
		}
		logger.setDatalog(points);
		return logger;
	}
	
	public void setFile(String filename)
	{
		file = new File(filename);
		try {
			sc = new Scanner(file);
		}
		catch(FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}
}
