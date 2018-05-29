BEGIN
  EXECUTE IMMEDIATE 'drop table fully_extracted_weather CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table FULLY_EXTRACTED_AIRPORT CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
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
  from AIRPORT
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
from WEATHER_RECORD)
;

COMMIT;