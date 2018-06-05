--select count(*) from D_AIRPORT;
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
    where OPERATION_CODE = 'INS' AND  ICAO_AIRPORT_CODE not in (select ICAO_AIRPORT_CODE from D_AIRPORT)
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
   a.SURR_KEY_AIRPORT,
   (select id_date from d_date where
       d_date.year = TO_CHAR(extract(year from t.THE_DATE))  AND
       d_date.month= to_char(extract(month from t.THE_DATE)) AND
       d_date.day = to_char(extract(day from t.THE_DATE))),
   (select id_time from d_time where
     d_time.hour = to_char(t.TIME, 'HH24') AND
     d_time.minute = to_char(t.TIME, 'MI') AND
     d_time.second = to_char(t.TIME, 'SS')),
    a.ICAO_AIRPORT_CODE,
    t.WIND_DIRECTION,
    t.WIND_DIRECTION_FROM,
    t.WIND_DIRECTION_TO,
    t.WIND_SPEED,
    t.TEMPERATURE,
    t.DEW_POINT
  from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME t
  join D_AIRPORT a on (a.ICAO_AIRPORT_CODE = t.ICAO_AIRPORT_CODE)
);



--select ICAO_AIRPORT_CODE, count(*) from F_WEATHER_RECORD where ID_DATE = -1 OR ID_TIME = -1 group by
-- ICAO_AIRPORT_CODE;

--select count(*) from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME;

--select count(*) from F_WEATHER_RECORD;
/*
select f.SURR_KEY_AIRPORT, f.ID_TIME, f.ID_DATE, oc.count
  from F_WEATHER_RECORD f
inner join (
  select SURR_KEY_AIRPORT, ID_TIME, ID_DATE, COUNT(*) as count
    from F_WEATHER_RECORD
    group by SURR_KEY_AIRPORT, ID_TIME, ID_DATE
    having count(*) > 1
) oc on f.SURR_KEY_AIRPORT = oc.SURR_KEY_AIRPORT and f.ID_TIME = oc.ID_TIME and f.ID_DATE = oc.ID_DATE;
*/

--select count(*) from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME where time is null group by ICAO_AIRPORT_CODE ;
COMMIT ;
