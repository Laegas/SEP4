BEGIN
  EXECUTE IMMEDIATE 'drop table F_WEATHER_RECORD CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table D_AIRPORT CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/

CREATE table D_AIRPORT(
  surr_key_airport int primary key,
  ICAO_Airport_Code VARCHAR(4),
  altitude int,
  airport_longitude varchar(8),
  airport_latitude varchar(7),
  airport_name varchar(50),
  WMO_Index varchar(5),
  id_valid_from int references D_DATE(ID_DATE),
  id_valid_to int references D_DATE(ID_DATE)
);
CREATE table F_WEATHER_RECORD(
  surr_key_airport int references D_Airport(surr_key_airport),
  id_time int references D_TIME(ID_TIME),
  id_date int references D_DATE(ID_DATE),
  wind_direction int,
  wind_direction_from int,
  wind_direction_to int,
  wind_speed int ,
  temperature decimal (5,2),
  dew_point decimal (5,2),
  PRIMARY KEY (surr_key_airport, id_time, id_date)
);

COMMIT;