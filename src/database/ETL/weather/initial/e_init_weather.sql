BEGIN
  EXECUTE IMMEDIATE 'drop table fully_extracted_weather CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table FULLY_EXTRACTED_AIRPORT CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table AIRPORTS_YESTERDAY CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table last_Date_Of_Airport_Extraction CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/

CREATE TABLE last_Date_Of_Airport_Extraction (lastDate date);
INSERT INTO last_Date_Of_Airport_Extraction (lastDate) VALUES (to_date('0001-01-01', 'YYYY-MM-DD'));

create table FULLY_EXTRACTED_AIRPORT as
  (
    select
      ICAO_airport_Code,
      latitude,
      longitude,
      countryName,
      airportName,
      altitude,
      wmo_index
  from AIRPORT where 1=0
  );
alter table FULLY_EXTRACTED_AIRPORT
  add operation_code varchar2(3);

create table FULLY_EXTRACTED_WEATHER as
  (select
  id as weather_id,
  ICAO_airport_code,
  wind_direction,
  wind_speed,
  wind_direction_from,
  wind_direction_to,
  temperature,
  dew_point,
  the_date,
  HOUR,
  MINUTE
from WEATHER_RECORD where 1=0)
;

CREATE TABLE AIRPORTS_YESTERDAY as (SELECT * FROM AIRPORT where 1 = 0);

COMMIT;