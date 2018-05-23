select * from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME;

insert into F_WEATHER_RECORD (
    SURR_KEY_WEATHER,
    SURR_KEY_FLIGHT,
    W_DATE,
    W_TIME,
    WIND_DIRECTION,
    WIND_DIRECTION_FROM,
    WIND_DIRECTION_TO,
    WIND_SPEED,
    TEMPERATURE,
    DEW_POINT,
    AIRPORT_CODE
    ) (select
    WEATHER_ID,
    (select SURR_KEY_FLIGHT from D_FLIGHT f where f.START_DATE = THE_DATE) as surr_key_flight,
    THE_DATE,
    TIME,
    WIND_DIRECTION,
    WIND_DIRECTION_FROM,
    WIND_DIRECTION_TO,
    WIND_SPEED,
    TEMPERATURE,
    DEW_POINT,
    ICAO_AIRPORT_CODE
  from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME
    where WEATHER_ID not IN (select SURR_KEY_WEATHER from F_WEATHER_RECORD)
);


COMMIT ;