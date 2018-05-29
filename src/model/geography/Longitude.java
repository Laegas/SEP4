package model.geography;

import model.time.Minute;

import java.text.DecimalFormat;

import static config.VisualizationConfig.*;

public class Longitude {
	private Degree degree;
	private Minute minute;
	private Second second;

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

	public double getSecond() { return second.getSecond(); }

	public double getDecimal() {
		return second.getSecondAsDecimal();
	}

	public void setDecimal(double decimal) {
		this.second = new Second(decimal);
	}

	public String toDatabase()
	{
		String t = "";
		int decimal = (int ) (second.getSecondAsDecimal()*1000);

		String degree = String.format("%03d", getDegree());
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

	public static String convertToLongitude(int x) {
		if(x > WIDTH || x < 0)
			throw new IllegalArgumentException("Argument has to be from 0 to " + WIDTH);
		int degreeValue = (int)(LONGITUDE_START + x / (WIDTH / (LONGITUDE_END - LONGITUDE_START)));
		int minuteValue = (x / (int)(1 / WEST_TO_EAST_ARC) % 60);
		int secondValue = (x % (int)(1 / WEST_TO_EAST_ARC)) * (60 / (int)(1 / WEST_TO_EAST_ARC));
		return degreeValue + "°" + minuteValue + "'" + secondValue + "''";
	}

	public static double convertToLongitudeDouble(int x) {
		if(x > WIDTH || x < 0)
			throw new IllegalArgumentException("Argument has to be from 0 to " + WIDTH);
		return ((double)x / WIDTH) * (LONGITUDE_END - LONGITUDE_START) + LONGITUDE_START;
	}

	public int getGridIndex() throws InvalidCoordinatesException {
		int index = 0;
		index += (getDegree() - (int)(LONGITUDE_START)) * (int)(WIDTH / (LONGITUDE_END - LONGITUDE_START));
		index += (int)(getMinute() - (LONGITUDE_START % 1 * 60.0)) * (int)(WIDTH / (LONGITUDE_END - LONGITUDE_START)
				/ 60.0);
		index += (int)(getSecond()) / (int)(60 * WEST_TO_EAST_ARC);
		if(index < 0 || index > WIDTH)
			throw new InvalidCoordinatesException("Latitude is " + getDegree() + "°" + getMinute() + "'" + getSecond
					() + "'' and expected between " + LONGITUDE_START + " and " + LONGITUDE_END);
		return index;
	}

	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(!(obj instanceof Longitude))
			return false;
		Longitude o = (Longitude)obj;
		return o.degree.equals(this.degree) &&
				o.minute.equals(this.minute) &&
				o.second.equals(this.second);
	}
	public String toDBString() {
		return String.format("%03d", getDegree()) + String.format("%02d", getMinute()) + String.format("%03d", (int) (getSecond() * 1000));

	}
}
