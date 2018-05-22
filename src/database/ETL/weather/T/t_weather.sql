insert into TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME (
        WEATHER_ID,
        ICAO_AIRPORT_CODE,
        WIND_DIRECTION,
        WIND_SPEED,
        WIND_DIRECTION_FROM,
        WIND_DIRECTION_TO,
        TEMPERATURE,
        DEW_POINT,
        THE_DATE,
        TIME
  )
  (
    SELECT
        WEATHER_ID,
        ICAO_AIRPORT_CODE,
        WIND_DIRECTION,
        WIND_SPEED,
        WIND_DIRECTION_FROM,
        WIND_DIRECTION_TO,
        TEMPERATURE,
        DEW_POINT,
        THE_DATE,
        to_Timestamp( (HOUR || ':' || MINUTE), 'HH24:MI')
    FROM FULLY_EXTRACTED_WEATHER
  );