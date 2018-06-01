package model.igc;

import model.time.Date;
import java.util.ArrayList;
import java.util.List;

public class Flight {
	private List<DataPoint> datapoints;
	private Date date;
	private FlightID flightID;
	private Glider glider;

	public Flight(Date date, List<DataPoint> datalog, Glider glider, int flight_id) {
		this.date = date;
		this.datapoints = datalog;
		this.glider = glider;
		this.flightID = new FlightID(flight_id);
	}
	public Flight(Date date)
	{
		this.date = date;
		this.datapoints = new ArrayList<>();
		this.glider=null;
		this.flightID=null;
	}
	public Flight(Date date, List<DataPoint> datalog, Glider glider)
	{
		this.date = date;
		this.datapoints = datalog;
		this.glider = glider;
		this.flightID=null;
	}

	public int getFlight_id() {
		return flightID.getFlightID();
	}

	public void setFlight_id(int flight_id) {
		this.flightID.setFlightID(flight_id);
	}

	public Flight(Date date, int flight_id, Glider glider)
	{
		this.date=date;
		this.flightID = new FlightID(flight_id);
		this.glider = glider;
		this.datapoints = null;

	}


	@Override
	public String toString() {
		return "Flight [datalog=" + datapoints + ", date=" + date + ", gliderID=" + glider.getGlider_id() + "]";
	}
	public List<DataPoint> getDatalog() {
		return datapoints;
	}

	public Date getDate() {
		return date;
	}
	
	public void setDatalog(ArrayList<DataPoint> datalog)
	{
		this.datapoints = datalog;
	}

	public Glider getGlider() {
		return glider;
	}

	public void setGlider(Glider glider) {
		this.glider = glider;
	}
}
