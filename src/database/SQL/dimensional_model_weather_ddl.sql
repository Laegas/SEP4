BEGIN
  EXECUTE IMMEDIATE 'drop table F_WEATHER_RECORD CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/


CREATE table F_WEATHER_RECORD(
  surr_key_weather INT,
  surr_key_igc_weather INT REFERENCES  D_IGC_WEATHER(SURR_KEY_IGC_WEATHER),
  w_date DATE ,
  w_time TIMESTAMP,
  wind_direction int,
  wind_direction_from int,
  wind_direction_to int,
  wind_speed int ,
  temperature decimal (5,2),
  dew_point decimal (5,2),
  airport_code varchar(4),

  PRIMARY KEY (surr_key_weather, surr_key_igc_weather)
);

COMMIT;