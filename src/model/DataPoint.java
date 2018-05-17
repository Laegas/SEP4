package model;

import java.sql.Time;

public class DataPoint {
	private char recordType;
	private Time time;
	private Longitude longitude;
	private Latitude latitude;
	private char sataliteCoverage;
	private int pressureAltitude;
	private int GPSAltitude;
	
	public DataPoint(char recordType, Time time, Longitude longitude, Latitude latitude, char sataliteCoverage, int pressureAltitude, int GPSAltitude) {
		this.recordType=recordType;
		this.time=time;
		this.longitude=longitude;
		this.latitude= latitude;
		this.sataliteCoverage=sataliteCoverage;
		this.pressureAltitude=pressureAltitude;
		this.GPSAltitude=GPSAltitude;
	}

	public char getRecordType() {
		return recordType;
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
		return "DataPoint [recordType=" + recordType + ", time=" + time + ", longitude=" + longitude + ", latitude="
				+ latitude + ", sataliteCoverage=" + sataliteCoverage + ", pressureAltitude=" + pressureAltitude
				+ ", GPSAltitude=" + GPSAltitude + "]";
	}

	public char getSataliteCoverage() {
		return sataliteCoverage;
	}

	public int getPressureAltitude() {
		return pressureAltitude;
	}

	public int getGPSAltitude() {
		return GPSAltitude;
	}
	
	
	
	
}
