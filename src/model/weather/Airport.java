package model.weather;

import model.geography.CountryName;
import model.geography.Latitude;
import model.geography.Longitude;

public class Airport {
    private ICAOAirportCode airport;
    private Latitude latitude;
    private Longitude longitude;
    private Altitude altitude;
    private WeatherRecord[] weatherRecords;
    private CountryName countryName;
    private String airportName;
    private WMOIndex wmoIndex;

    public Airport(ICAOAirportCode ICAO,Latitude latitude,Longitude longitude,Altitude altitude,WeatherRecord[] weatherRecords,CountryName countryName, String airportName, WMOIndex wmoIndex)
    {
        setAirport(ICAO);
        setLatitude(latitude);
        setLongitude(longitude);
        setAltitude(altitude);
        setWeatherRecords(weatherRecords);
        setAirportName(airportName);
        setWmoIndex(wmoIndex);
        setCountryName(countryName);
    }
    public Airport()
    {
        this.airport = null;
        this.latitude=null;
        this.longitude=null;
        this.altitude=null;
        this.weatherRecords=null;
        this.countryName=null;
        this.airportName = null;
    }

    public void setWmoIndex(WMOIndex wmoIndex) {
        this.wmoIndex = wmoIndex;
    }

    public void setCountryName(CountryName countryName) {
        this.countryName = countryName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName;
    }

    public void setAirport(ICAOAirportCode airport) {
        this.airport = airport;
    }

    public void setAltitude(Altitude altitude) {
        this.altitude = altitude;
    }

    public void setLatitude(Latitude latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Longitude longitude) {
        this.longitude = longitude;
    }

    public void setWeatherRecords(WeatherRecord[] weatherRecords) {
        this.weatherRecords = weatherRecords;
    }

    public WMOIndex getWmoIndex() {
        return wmoIndex;
    }

    public String getAirportName() {
        return airportName;
    }

    public CountryName getCountryName() {
        return countryName;
    }

    public WeatherRecord[] getWeatherRecords() {
        return weatherRecords;
    }

    public Altitude getAltitude() {
        return altitude;
    }

    public ICAOAirportCode getAirport() {
        return airport;
    }

    public Latitude getLatitude() {
        return latitude;
    }

    public Longitude getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        String t ="";
        t+=airport.getICAOCode()+", "+airportName+" ("+countryName.getCountryName()+")\nWMO index: "+wmoIndex.getWMOIndex()+"\n"+"Latitude "+latitude.getDegree()+"-"
                +latitude.getMinute()+"N. Longitude "+longitude.getDegree()+"-"+longitude.getMinute()+"E. Altitude "+altitude.getAltitude()+" m.";
        return t;
    }
}
