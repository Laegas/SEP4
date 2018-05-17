package model;

import static visualization.VisualizationConfig.*;

public class Latitude {
	private int degree;
	private int minute;
	private int decimal;
	
	public Latitude(int degree, int minute, int decimal)
	{
		this.decimal=decimal;
		this.minute=minute;
		this.degree=degree;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public int getDecimal() {
		return decimal;
	}

	public void setDecimal(int decimal) {
		this.decimal = decimal;
	}

	public static String convertToLatitude(int y) {
		if(y > HEIGHT || y < 0)
			throw new IllegalArgumentException("Argument has to be from 0 to " + HEIGHT);
		int degreeValue = (int)(LATITUDE_START + y / (HEIGHT / (LATITUDE_END - LATITUDE_START)));
		int minuteValue = (int)((LATITUDE_START % 1) * 60 + (y / (int)(1 / SOUTH_TO_NORTH_ARC))) % 60;
		int secondValue = (y % (int)(1 / SOUTH_TO_NORTH_ARC)) * (60 / (int)(1 / SOUTH_TO_NORTH_ARC));
		return degreeValue + "°" + minuteValue + "'" + secondValue + "''";
	}
}
