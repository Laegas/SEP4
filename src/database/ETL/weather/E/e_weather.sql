-- select column_name || ', ' from user_tab_columns where table_name = 'WEATHER_RECORD';

insert into FULLY_EXTRACTED_WEATHER (
        WEATHER_ID,
        ICAO_AIRPORT_CODE,
        WIND_DIRECTION,
        WIND_SPEED,
        WIND_DIRECTION_FROM,
        WIND_DIRECTION_TO,
        TEMPERATURE,
        DEW_POINT,
        THE_DATE,
        HOUR,
        MINUTE
  )
  (
      SELECT
        id,
        ICAO_airport_code,
        wind_direction,
        wind_speed,
        wind_direction_from,
        wind_direction_to,
        temperature,
        dew_point,
        the_date,
        hour,
        minute
       FROM WEATHER_RECORD
          WHERE id NOT IN (SELECT WEATHER_ID
                                FROM FULLY_EXTRACTED_WEATHER) --extract only new ones
  );

insert into FULLY_EXTRACTED_AIRPORT(
    ICAO_AIRPORT_CODE,
    LATITUDE,
    LONGITUDE,
    COUNTRYNAME,
    AIRPORTNAME,
    ALTITUDE,
    WMO_INDEX,
    OPERATION_CODE
)(
    select
      ICAO_AIRPORT_CODE,
      LATITUDE,
      LONGITUDE,
      COUNTRYNAME,
      AIRPORTNAME,
      ALTITUDE,
      WMO_INDEX,
      'INS'
    from AIRPORT
    where ICAO_AIRPORT_CODE in
    (SELECT ICAO_AIRPORT_CODE from AIRPORT
     minus
     SELECT ICAO_AIRPORT_CODE from AIRPORTS_YESTERDAY
    )
);

insert into FULLY_EXTRACTED_AIRPORT(
  ICAO_AIRPORT_CODE,
  LATITUDE,
  LONGITUDE,
  COUNTRYNAME,
  AIRPORTNAME,
  ALTITUDE,
  WMO_INDEX,
  OPERATION_CODE
)(
  select
    ICAO_AIRPORT_CODE,
    LATITUDE,
    LONGITUDE,
    COUNTRYNAME,
    AIRPORTNAME,
    ALTITUDE,
    WMO_INDEX,
    'DEL'
  from AIRPORT
  where ICAO_AIRPORT_CODE in
        (SELECT ICAO_AIRPORT_CODE FROM AIRPORTS_YESTERDAY
         minus
         SELECT ICAO_AIRPORT_CODE from AIRPORT
        )
);

insert into FULLY_EXTRACTED_AIRPORT(
  ICAO_AIRPORT_CODE,
  LATITUDE,
  LONGITUDE,
  COUNTRYNAME,
  AIRPORTNAME,
  ALTITUDE,
  WMO_INDEX,
  OPERATION_CODE
)(
  select
    ICAO_AIRPORT_CODE,
    LATITUDE,
    LONGITUDE,
    COUNTRYNAME,
    AIRPORTNAME,
    ALTITUDE,
    WMO_INDEX,
    OPERATION_CODE
  from
    ((select ICAO_AIRPORT_CODE,
           LATITUDE,
           LONGITUDE,
           COUNTRYNAME,
           AIRPORTNAME,
           ALTITUDE,
           WMO_INDEX,
           'CHG' as OPERATION_CODE from AIRPORT)
          minus
           (select ICAO_AIRPORT_CODE,
            LATITUDE,
            LONGITUDE,
            COUNTRYNAME,
            AIRPORTNAME,
            ALTITUDE,
            WMO_INDEX,
            'CHG' as OPERATION_CODE from AIRPORTS_YESTERDAY))
  where ICAO_AIRPORT_CODE not in
        (SELECT ICAO_AIRPORT_CODE from FULLY_EXTRACTED_AIRPORT where OPERATION_CODE = 'INS')
);
COMMIT ;

select * from FULLY_EXTRACTED_AIRPORT;