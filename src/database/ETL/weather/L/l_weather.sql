
insert into D_AIRPORT(
    SURR_KEY_AIRPORT,
    ICAO_AIRPORT_CODE,
    ALTITUDE,
    AIRPORT_LONGITUDE,
    AIRPORT_LATITUDE,
    AIRPORT_NAME,
    WMO_INDEX,
    COUNTRY_NAME,
    VALID_FROM,
    VALID_TO
) (select
    AIRPORT_ID.nextval,
    ICAO_AIRPORT_CODE,
    ALTITUDE,
    LONGITUDE,
    LATITUDE,
    AIRPORTNAME,
    WMO_INDEX,
    COUNTRYNAME,
    trunc(sysdate, 'DAY') as valid_from,
    to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') as valid_to
    from FULLY_EXTRACTED_AIRPORT
    where OPERATION_CODE = 'INS'
    );

    update D_AIRPORT set
      valid_to = (trunc(sysdate-1,'DAY'))
    where valid_to = to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') AND
          ICAO_AIRPORT_CODE in (select ICAO_AIRPORT_CODE from FULLY_EXTRACTED_AIRPORT
          where OPERATION_CODE = 'DEL')
    ;

    update D_AIRPORT set
      valid_to = (trunc(sysdate-1,'DAY'))
    where valid_to = to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') AND
          ICAO_AIRPORT_CODE in (select ICAO_AIRPORT_CODE from FULLY_EXTRACTED_AIRPORT
          where OPERATION_CODE = 'CHG')
    ;

insert into F_WEATHER_RECORD (
    SURR_KEY_AIRPORT,
    ID_DATE,
    ID_TIME,
    ICAO_AIRPORT_CODE,
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
     d_time.hour = to_char(TIME, 'HH') AND
     d_time.minute = to_char(TIME, 'MI') AND
     d_time.second = to_char(TIME, 'SS')),
    ICAO_AIRPORT_CODE,
    WIND_DIRECTION,
    WIND_DIRECTION_FROM,
    WIND_DIRECTION_TO,
    WIND_SPEED,
    TEMPERATURE,
    DEW_POINT
  from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME t
);


COMMIT ;