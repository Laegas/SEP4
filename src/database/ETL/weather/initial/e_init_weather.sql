BEGIN
  EXECUTE IMMEDIATE 'drop table fully_extracted_weather CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
/*create table FULLY_EXTRACTED_AIRPORT as
  (
      select

  );*/
create table FULLY_EXTRACTED_WEATHER as
  (select
  W_ID as weather_id,
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