BEGIN DROP_TABLE('fully_extracted_weather'); END;
/
BEGIN DROP_TABLE('FULLY_EXTRACTED_AIRPORT'); END;
/
BEGIN DROP_TABLE('last_Date_Of_Weather_Extraction'); END;
/
BEGIN DROP_TABLE('AIRPORTS_YESTERDAY'); END;
/

CREATE TABLE last_Date_Of_Weather_Extraction (lastDate date);
INSERT INTO last_Date_Of_Weather_Extraction (lastDate) VALUES (to_date('0001-01-01', 'YYYY-MM-DD'));

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