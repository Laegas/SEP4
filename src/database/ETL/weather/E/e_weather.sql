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
        W_ID,
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
   FROM WEATHER_RECORD
      WHERE W_ID NOT IN (SELECT SURR_KEY_WEATHER
                            FROM F_WEATHER_RECORD) --extract only new ones
  );

COMMIT ;