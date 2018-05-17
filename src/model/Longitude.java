package model;

import static visualization.VisualizationConfig.*;

public class Longitude {
	private int degree;
	private int minute;
	private int decimal;
	
	public Longitude(int degree, int minute, int decimal)
	{
		this.degree=degree;
		this.minute=minute;
		this.decimal=decimal;
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

	public static String convertToLongitude(int x) {
		if(x > WIDTH || x < 0)
			throw new IllegalArgumentException("Argument has to be from 0 to " + WIDTH);
		int degreeValue = (int)(LONGITUDE_START + x / (WIDTH / (LONGITUDE_END - LONGITUDE_START)));
		int minuteValue = (x / (int)(1 / WEST_TO_EAST_ARC) % 60);
		int secondValue = (x % (int)(1 / WEST_TO_EAST_ARC)) * (60 / (int)(1 / WEST_TO_EAST_ARC));
		return degreeValue + "°" + minuteValue + "'" + secondValue + "''";
	}
}
