package model.igc;

import model.time.Date;
import java.util.ArrayList;

public class Flight {
	private ArrayList<DataPoint> datapoints;
	private Date date;
	private String gliderID;

	public Flight(Date date, ArrayList<DataPoint> datalog, String gliderID)
	{
		this.date=date;
		this.datapoints= datalog;
	}
	public Flight(Date date)
	{
		this.date=date;
	}

	@Override
	public String toString() {
		return "Flight [datalog=" + datapoints + ", date=" + date + ", gliderID=" + gliderID + "]";
	}
	public ArrayList<DataPoint> getDatalog() {
		return datapoints;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDatalog(ArrayList<DataPoint> datalog)
	{
		this.datapoints = datalog;
	}
	
	public void setGliderID(String ID)
	{
		this.gliderID=ID;
	}
	
	public String getGliderID()
	{
		return gliderID;
	}

	
}
