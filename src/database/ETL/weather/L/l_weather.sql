select * from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME;

insert into D_AIRPORT(

);

insert into F_WEATHER_RECORD (
    SURR_KEY_AIRPORT,
    ID_DATE,
    ID_TIME,
    WIND_DIRECTION,
    WIND_DIRECTION_FROM,
    WIND_DIRECTION_TO,
    WIND_SPEED,
    TEMPERATURE,
    DEW_POINT,
    AIRPORT_CODE
    ) (select
    WEATHER_ID,
    1 as surr_key_flight, -- this is going to be deleted anyways
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