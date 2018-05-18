package model.igc;

import model.time.Date;
import java.util.ArrayList;
import java.util.List;

public class Flight {
	private List<DataPoint> datapoints;
	private Date date;
	private int flight_id;
	private Glider glider;

	public Flight(Date date, List<DataPoint> datalog, Glider glider, int flight_id) {
		this.date = date;
		this.datapoints = datalog;
		this.glider = glider;
		this.flight_id = flight_id;
	}

    public Flight(Date date) {
        this.date = date;
    }

    public int getFlight_id() {
		return flight_id;
	}

	public void setFlight_id(int flight_id) {
		this.flight_id = flight_id;
	}

	public Flight(Date date, int flight_id)
	{
		this.date=date;
		this.flight_id = flight_id;
		this.glider = null;
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
