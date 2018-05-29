select * from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME;

insert into D_AIRPORT(
    SURR_KEY_AIRPORT,
    ICAO_AIRPORT_CODE,
    ALTITUDE,
    AIRPORT_LONGITUDE,
    AIRPORT_LATITUDE,
    AIRPORT_NAME,
    WMO_INDEX,
    ID_VALID_FROM,
    ID_VALID_TO
) (select
    AIRPORT_ID.nextval,
    ICAO_AIRPORT_CODE,
    ALTITUDE,
    AIRPORT_LONGITUDE,
    AIRPORT_LATITUDE,
    AIRPORT_NAME,
    WMO_INDEX,
    1, --placeholders for the moment, until fix valid to and from and keep operation carried out on data
    0
    from FULLY_EXTRACTED_AIRPORT);

insert into F_WEATHER_RECORD (
    SURR_KEY_AIRPORT,
    ID_DATE,
    ID_TIME,
    WIND_DIRECTION,
    WIND_DIRECTION_FROM,
    WIND_DIRECTION_TO,
    WIND_SPEED,
    TEMPERATURE,
    DEW_POINT
    ) (select
   (select SURR_KEY_AIRPORT from D_AIRPORT a where (a.ICAO_AIRPORT_CODE = t.ICAO_AIRPORT_CODE)),
   (select id_date from d_date where
       d_date.year = extract(year from THE_DATE) AND
       d_date.month= extract(month from THE_DATE) AND
       d_date.day = extract(day from THE_DATE)),
   (select id_time from d_time where
     d_time.hour = extract(hour from TIME) AND
     d_time.minute= extract(minute from TIME) AND
     d_time.second = extract(second from TIME)),
    WIND_DIRECTION,
    WIND_DIRECTION_FROM,
    WIND_DIRECTION_TO,
    WIND_SPEED,
    TEMPERATURE,
    DEW_POINT,
    ICAO_AIRPORT_CODE
  from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME t
    where WEATHER_ID not IN (select SURR_KEY_WEATHER from F_WEATHER_RECORD)
);


COMMIT ;