select * from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME;

begin
    for w in (SELECT
                 * from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME
                 where WEATHER_ID not IN (select SURR_KEY_WEATHER from F_WEATHER_RECORD)
              ) loop

        --inserting key
        insert into D_IGC_WEATHER( SURR_KEY_IGC_WEATHER) VALUES (
            IGC_WEATHER_SURR_KEY.nextval
        );

        insert into F_WEATHER_RECORD (
            SURR_KEY_WEATHER,
            SURR_KEY_IGC_WEATHER,
            W_DATE,
            W_TIME,
            WIND_DIRECTION,
            WIND_DIRECTION_FROM,
            WIND_DIRECTION_TO,
            WIND_SPEED,
            TEMPERATURE,
            DEW_POINT,
            AIRPORT_CODE
        ) VALUES (
               w.WEATHER_ID,
               IGC_WEATHER_SURR_KEY.currval,
               w.THE_DATE,
               w.TIME,
               w.WIND_DIRECTION,
               w.WIND_DIRECTION_FROM,
               w.WIND_DIRECTION_TO,
               w.WIND_SPEED,
               w.TEMPERATURE,
               w.DEW_POINT,
               w.ICAO_AIRPORT_CODE
        );
    end loop;
end;
/



select * from F_WEATHER_RECORD;
COMMIT ;