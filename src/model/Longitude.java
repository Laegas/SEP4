package model;

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
}
