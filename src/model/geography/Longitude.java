package model.geography;

import model.time.Minute;

import static config.VisualizationConfig.*;

public class Longitude {
	private Degree degree;
	private Minute minute;
	private Second second;
	
	public Longitude(int degree, int minute, int second)
	{
		this.second= new Second(second);
		this.minute= new Minute(minute);
		this.degree= new Degree(degree);
	}

	public Longitude(int degree, int minute, double decimal)
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
		t+= degree.getDegree()+""+minute.getMinute()+""+second.getSecondAsDecimal();
		return t;
	}
	public String toString()
	{
		String t = "";
		t+= degree.getDegree()+""+minute.getMinute()+""+second.getSecondAsDecimal();
		return t;
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
