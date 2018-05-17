package model;

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
	
}
