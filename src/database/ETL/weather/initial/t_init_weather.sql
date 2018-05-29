BEGIN
  EXECUTE IMMEDIATE 'drop table transform_weather_hour_minute_to_time CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/

create table transform_weather_hour_minute_to_time as
  (select
  WEATHER_ID,
  ICAO_airport_code,
  wind_direction,
  wind_speed,
  wind_direction_from,
  wind_direction_to,
  temperature,
  dew_point,
  the_date
from FULLY_EXTRACTED_WEATHER)
;

alter table transform_weather_hour_minute_to_time
  ADD time timestamp
;

COMMIT;