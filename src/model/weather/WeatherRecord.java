package model.weather;

import model.time.*;

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
    Hour hour;
    Minute minute;


    public WeatherRecord() {
    }

    public Hour getHour() {
        return hour;
    }

    public void setHour(Hour hour) {
        this.hour = hour;
    }

    public Minute getMinute() {
        return minute;
    }

    public void setMinute(Minute minute) {
        this.minute = minute;
    }

    public WeatherRecord(ICAOAirportCode airportCode, Wind wind, VaryingWindDirection varyingWindDirection, DegreeCelcius temperature, DegreeCelcius dewPoint, Day day, Month month, Year year, Hour hour, Minute minute) {

        this.airportCode = airportCode;
        this.wind = wind;
        this.varyingWindDirection = varyingWindDirection;
        this.temperature = temperature;
        this.dewPoint = dewPoint;
        this.day = day;
        this.month = month;
        this.year = year;
        this.hour = hour;
        this.minute = minute;
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

    public Day getDayOfMonth() {
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
