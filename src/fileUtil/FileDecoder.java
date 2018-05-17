package fileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import model.DataLogger;
import model.DataPoint;
import model.Latitude;
import model.Longitude;

public class FileDecoder {
	private Scanner sc;
	private File file;
	public static final String FILENAME_DIRECTORY = "D:\\VIA\\VIA lectures+presentations\\4th Semester\\SEP4\\SampleIGCFile.igc";
	
	public FileDecoder(String filename)
	{
		this.file= new File(filename);
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public DataLogger readFile()
	{
		ArrayList<DataPoint> points = new ArrayList<>();
		DataLogger logger = null;
		
		
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String container = "";
		while (sc.hasNextLine()) {
			container = sc.nextLine();
			if (container.startsWith("B"))// DataPoint instance
			{
				char recordType = container.toCharArray()[0];
				String hour = container.substring(1,3);
				String minute = container.substring(3,5);
				String second = container.substring(5,7);
				Time time = new Time(Integer.parseInt(hour),Integer.parseInt(minute),Integer.parseInt(second));
				
				int latitudeDegree = Integer.parseInt(container.substring(7,9));
				int latitudeMinute = Integer.parseInt(container.substring(9,11));
				int latitudeDecimals = Integer.parseInt(container.substring(11,14));
				Latitude latitude = new Latitude(latitudeDegree,latitudeMinute,latitudeDecimals);
				
				int longitudeDegree = Integer.parseInt(container.substring(15,18));
				int longitudeMinute = Integer.parseInt(container.substring(18,20));
				int longitudeDecimals = Integer.parseInt(container.substring(20,23));
				Longitude longitude = new Longitude(longitudeDegree,longitudeMinute,longitudeDecimals);
				
				
				
				char satelliteCoverage = container.toCharArray()[24];
				int pressureAltitude = Integer.parseInt(container.substring(25,30));
				int GPSAltitude = Integer.parseInt(container.substring(30,35));
				
				points.add(new DataPoint(recordType, time, longitude, latitude, satelliteCoverage, pressureAltitude, GPSAltitude));
			}
			else if(container.startsWith("HFGIDGLIDERID")) // Glider ID
			{
				logger.setGliderID(container.substring(14));
			}
			else if (container.startsWith("HFDTE"))//  Date information
			{
				logger = new DataLogger(new Date(Integer.parseInt(container.substring(5, 7)),Integer.parseInt(container.substring(7, 9)),Integer.parseInt(container.substring(9, 11))));
			}
			else if(container.startsWith("LMCU::ENDINFO")) // When DataLogger instance is done
			{
				logger.setDatalog(points);
				return logger;
			}
			
		}
		return logger;
	}
	
	public void setFile(String filename)
	{
		file = new File(filename);
	}
}
