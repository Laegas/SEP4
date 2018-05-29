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
     ICAO_AIRPORT_CODE,
     WIND_DIRECTION,
     WIND_SPEED,
     WIND_DIRECTION_FROM,
     WIND_DIRECTION_TO,
     TEMPERATURE,
     DEW_POINT,
     THE_DATE
from FULLY_EXTRACTED_WEATHER)
;

alter table transform_weather_hour_minute_to_time
  ADD time timestamp
;

COMMIT;