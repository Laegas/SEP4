package model.weather;

import model.time.Day;
import model.time.Month;
import model.time.Year;

/**
 * Created by kenneth on 17/05/2018.
 */
public class WeatherRecord {
    ICAOAirportCode airportCode;
    Wind wind;
    VaryingWindDirection varyingWindDirection;
    DegreeCelcius temperature;
    DegreeCelcius dewPoint;
    Day day;
    Month month;
    Year year;
    //todo add an hour and minute object

    public WeatherRecord(ICAOAirportCode airportCode, Wind wind, VaryingWindDirection varyingWindDirection, DegreeCelcius temperature, DegreeCelcius dewPoint, Day day, Month month, Year year) {
        this.airportCode = airportCode;
        this.wind = wind;
        this.varyingWindDirection = varyingWindDirection;
        this.temperature = temperature;
        this.dewPoint = dewPoint;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public ICAOAirportCode getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(ICAOAirportCode airportCode) {
        this.airportCode = airportCode;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public VaryingWindDirection getVaryingWindDirection() {
        return varyingWindDirection;
    }

    public void setVaryingWindDirection(VaryingWindDirection varyingWindDirection) {
        this.varyingWindDirection = varyingWindDirection;
    }

    public DegreeCelcius getTemperature() {
        return temperature;
    }

    public void setTemperature(DegreeCelcius temperature) {
        this.temperature = temperature;
    }

    public DegreeCelcius getDewPoint() {
        return dewPoint;
    }

    public void setDewPoint(DegreeCelcius dewPoint) {
        this.dewPoint = dewPoint;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Month getMonth() {
        return month;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public void setYear(Year year) {
        this.year = year;
    }
}
