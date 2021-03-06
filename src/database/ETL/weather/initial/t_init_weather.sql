BEGIN DROP_TABLE('transform_weather_hour_minute_to_time'); END;
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
from FULLY_EXTRACTED_WEATHER where 1 = 0)
;

alter table transform_weather_hour_minute_to_time
  ADD time timestamp
;

COMMIT;