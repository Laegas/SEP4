package model.igc;

import model.geography.Latitude;
import model.geography.Longitude;

import model.time.Time;

import javax.xml.crypto.Data;

public class DataPoint implements Comparable<DataPoint>{
	private Time time;
	private Longitude longitude;
	private Latitude latitude;
	private char satelliteCoverage;
	private int pressureAltitude;
	private int GPSAltitude;

	private int glider_id;
	private int flight_id;
	private int time_id;

	public DataPoint(Time time, Longitude longitude, Latitude latitude, char satelliteCoverage, int pressureAltitude, int GPSAltitude, int glider_id, int flight_id, int time_id) {
		this.time = time;
		this.longitude = longitude;
		this.latitude = latitude;
		this.satelliteCoverage = satelliteCoverage;
		this.pressureAltitude = pressureAltitude;
		this.GPSAltitude = GPSAltitude;
		this.glider_id = glider_id;
		this.flight_id = flight_id;
		this.time_id = time_id;
	}



	public int getGlider_id() {
		return glider_id;
	}

	public int getFlight_id() {
		return flight_id;
	}

	public int getTime_id() {
		return time_id;
	}

	public DataPoint(Time time, Longitude longitude, Latitude latitude, char satelliteCoverage, int pressureAltitude, int GPSAltitude) {
		this.time=time;
		this.longitude=longitude;
		this.latitude= latitude;
		this.satelliteCoverage=satelliteCoverage;
		this.pressureAltitude=pressureAltitude;
		this.GPSAltitude=GPSAltitude;
		this.time_id = time_id;

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


	@Override
	public int compareTo(DataPoint o) {
		return this.time.compareTo(o.getTime());
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) return true;
		if (object == null || getClass() != object.getClass()) return false;

		DataPoint dataPoint = (DataPoint) object;

		return time.equals(dataPoint.time);
	}


}
