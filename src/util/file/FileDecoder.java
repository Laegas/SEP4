package util.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.igc.Flight;
import model.igc.DataPoint;
import model.geography.Latitude;
import model.geography.Longitude;
import model.igc.Glider;
import model.time.*;
import model.weather.WeatherRecord;
import util.weatherUtil.MetarReader;
import util.weatherUtil.METARException;

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

	public WeatherRecord[] readMETARFile() {

		List<WeatherRecord> weatherRecords = new ArrayList<>();

		while (sc.hasNextLine()) {
			String l = sc.nextLine();
			if(l.startsWith("1") || l.startsWith("2")) {
				try {
					weatherRecords.add(MetarReader.decodeMetar(l));
				} catch (METARException e) {
					// ignore
				}
			}
		}

		WeatherRecord[] w = new WeatherRecord[weatherRecords.size()];
		for(int i = 0; i < w.length; i++)
			w[i] = weatherRecords.get(i);

		return w;
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
