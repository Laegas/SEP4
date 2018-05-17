package model.igc;

import java.util.Date;
import java.util.ArrayList;

public class DataLogger {
	private ArrayList<DataPoint> datalog;
	private Date date;
	private String gliderID;
	
	public DataLogger(Date date, ArrayList<DataPoint> datalog,String gliderID)
	{
		this.date=date;
		this.datalog= datalog;
	}
	public DataLogger(Date date)
	{
		this.date=date;
	}

	@Override
	public String toString() {
		return "DataLogger [datalog=" + datalog + ", date=" + date + ", gliderID=" + gliderID + "]";
	}
	public ArrayList<DataPoint> getDatalog() {
		return datalog;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDatalog(ArrayList<DataPoint> datalog)
	{
		this.datalog = datalog;
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
