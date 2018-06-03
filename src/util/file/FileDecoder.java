package util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.geography.CountryName;
import model.igc.Flight;
import model.igc.DataPoint;
import model.geography.Latitude;
import model.geography.Longitude;
import model.igc.Glider;
import model.time.*;
import model.weather.*;
import util.weatherUtil.MetarReader;
import util.weatherUtil.METARException;

public class FileDecoder {
	private Scanner sc;
	private File file;
	private BufferedReader br;
	private FileReader fr;

	public FileDecoder(String filename)
	{
		setFile(filename);
	}
    public FileDecoder()
    {
        //all null;
    }

	public Airport readMETARFile() {

		List<WeatherRecord> weatherRecords = new ArrayList<>();
		int count = 0;
		Airport airport = new Airport();
		while (sc.hasNextLine()) {
			String l = sc.nextLine();

			if(count==5)//info with ICAO code, airport full name and country name
			{
				airport.setAirport(new ICAOAirportCode(l.substring(2,6)));
				String[] info = l.split("\\(");
				airport.setAirportName(info[0].substring(7,info[0].length()-1).trim());
				airport.setCountryName(new CountryName(info[1].substring(0,info[1].length()-1)));
			}
			else if(count==6)//info with WMO index
			{
				String wmo = l.split(":")[1].trim();
				airport.setWmoIndex(new WMOIndex(wmo));
			}
			else if(count==7)//info with latitude, longitude and altitude
			{
                String[] info = l.split("\\.");
                airport.setLatitude(new Latitude(Integer.parseInt(info[0].substring(11,13)),Integer.parseInt(info[0].substring(14,16)),0));
                airport.setLongitude(new Longitude(Integer.parseInt(info[1].substring(11,14)),Integer.parseInt(info[1].substring(15,17)),0));
                airport.setAltitude(new Altitude(Integer.parseInt(info[2].substring(10,11))));
			}
			else if(l.startsWith("1") || l.startsWith("2")) {
				try {
					weatherRecords.add(MetarReader.decodeMetar(l));
				} catch (METARException e) {
					// ignore
				}
			}
			count++;
		}

		WeatherRecord[] w = new WeatherRecord[weatherRecords.size()];
		for(int i = 0; i < w.length; i++)
			w[i] = weatherRecords.get(i);
		airport.setWeatherRecords(w);
		return airport;
	}
	
	public Flight readIGCFile()
	{
		ArrayList<DataPoint> points = new ArrayList<>();
		Flight logger = null;
        String container;
        try
        {
		while ((container = br.readLine())!=null) {

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
				if(satelliteCoverage=='A')
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
			
		}}catch(Exception e)
        {
            e.printStackTrace();
        }
		logger.setDatalog(points);
		return logger;
	}
	
	public void setFile(String filename)
	{
		file = new File(filename);

		try {
            fr = new FileReader(filename);
            br = new BufferedReader(fr);
			sc = new Scanner(file);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
