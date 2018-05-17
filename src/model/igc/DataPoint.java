package model.igc;

import model.geography.Latitude;
import model.geography.Longitude;

import model.time.Time;

public class DataPoint {
	private Time time;
	private Longitude longitude;
	private Latitude latitude;
	private char satelliteCoverage;
	private int pressureAltitude;
	private int GPSAltitude;
	
	public DataPoint( Time time, Longitude longitude, Latitude latitude, char sataliteCoverage, int pressureAltitude, int GPSAltitude) {
		this.time=time;
		this.longitude=longitude;
		this.latitude= latitude;
		this.satelliteCoverage=sataliteCoverage;
		this.pressureAltitude=pressureAltitude;
		this.GPSAltitude=GPSAltitude;
	}



	public Time getTime() {
		return time;
	}

	public Longitude getLongitude() {
		return longitude;
	}

	public Latitude getLatitude() {
		return latitude;
	}

	@Override
	public String toString() {
		return "DataPoint [ time=" + time + ", longitude=" + longitude + ", latitude="
				+ latitude + ", satelliteCoverage=" + satelliteCoverage + ", pressureAltitude=" + pressureAltitude
				+ ", GPSAltitude=" + GPSAltitude + "]";
	}

	public char getSataliteCoverage() {
		return satelliteCoverage;
	}

	public int getPressureAltitude() {
		return pressureAltitude;
	}

	public int getGPSAltitude() {
		return GPSAltitude;
	}
	
	
	
	
}
