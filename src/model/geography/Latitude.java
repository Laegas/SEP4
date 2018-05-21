package model.geography;

import model.time.Minute;

import java.text.DecimalFormat;

import static config.VisualizationConfig.*;

public class Latitude {
	private Degree degree;
	private Minute minute;
	private Second second;

	public Latitude(int degree, int minute, double decimal)
	{
		this.second = new Second(decimal);
		this.minute= new Minute(minute);
		this.degree= new Degree(degree);
	}

	public int getDegree() {
		return degree.getDegree();
	}

	public void setDegree(int degree) {
		this.degree = new Degree(degree);
	}

	public int getMinute() {
		return minute.getMinute();
	}

	public void setMinute(int minute) {
		this.minute = new Minute(minute);
	}

	public double getDecimal() {
		return second.getSecondAsDecimal();
	}

	public void setDecimal(double decimal) {
		this.second = new Second(decimal);
	}

	public String toDatabase()
	{
		String t = "";
		int decimal = (int) (second.getSecondAsDecimal()*1000);

		String degree = String.format("%02d", getDegree());
		String minute = String.format("%02d", getMinute());
		String decimalString = String.format("%03d", decimal);
		t+=degree+minute+decimalString;
		return t;
	}

	public String toString()
	{
		String t = "";
		DecimalFormat formatter = new DecimalFormat("#0.000");
		t += (degree.getDegree() + "" + minute.getMinute() +""+ formatter.format(second.getSecondAsDecimal()));
		return t;
	}

	public static String convertToLatitude(int y) {
		if(y > HEIGHT || y < 0)
			throw new IllegalArgumentException("Argument has to be from 0 to " + HEIGHT);
		int degreeValue = (int)(LATITUDE_START + y / (HEIGHT / (LATITUDE_END - LATITUDE_START)));
		int minuteValue = (int)((LATITUDE_START % 1) * 60 + (y / (int)(1 / SOUTH_TO_NORTH_ARC))) % 60;
		int secondValue = (y % (int)(1 / SOUTH_TO_NORTH_ARC)) * (60 / (int)(1 / SOUTH_TO_NORTH_ARC));
		return degreeValue + "°" + minuteValue + "'" + secondValue + "''";
	}

	public static double convertToLatitudeDouble(int y) {
		if(y > HEIGHT || y < 0)
			throw new IllegalArgumentException("Argument has to be from 0 to " + HEIGHT);
		return LATITUDE_END - ((double)y / HEIGHT) * (LATITUDE_END - LATITUDE_START);
	}
}
