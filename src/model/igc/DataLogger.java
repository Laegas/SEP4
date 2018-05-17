package model.igc;

import model.time.Date;
import java.util.ArrayList;

public class DataLogger {
	private ArrayList<DataPoint> datapoints;
	private Date date;
	private String gliderID;

	public DataLogger(Date date, ArrayList<DataPoint> datalog,String gliderID)
	{
		this.date=date;
		this.datapoints= datalog;
	}
	public DataLogger(Date date)
	{
		this.date=date;
	}

	@Override
	public String toString() {
		return "DataLogger [datalog=" + datapoints + ", date=" + date + ", gliderID=" + gliderID + "]";
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
