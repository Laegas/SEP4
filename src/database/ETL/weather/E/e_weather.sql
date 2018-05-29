-- select column_name || ', ' from user_tab_columns where table_name = 'WEATHER_RECORD';

TRUNCATE table FULLY_EXTRACTED_WEATHER;

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

COMMIT ;