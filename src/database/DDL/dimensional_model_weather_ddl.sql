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
  ICAO_Airport_Code VARCHAR2(4),
  altitude int,
  airport_longitude varchar2(8),
  airport_latitude varchar2(7),
  airport_name varchar2(50),
  country_name varchar2(100),
  WMO_Index varchar2(5),
  valid_from date,
  valid_to date
);
CREATE table F_WEATHER_RECORD(
  surr_key_airport int references D_Airport(surr_key_airport),
  id_time int references D_TIME(ID_TIME),
  id_date int references D_DATE(ID_DATE),
  ICAO_airport_code varchar2(4),
  wind_direction int,
  wind_direction_from int,
  wind_direction_to int,
  wind_speed int ,
  temperature decimal (5,2),
  dew_point decimal (5,2),
  PRIMARY KEY (surr_key_airport, id_time, id_date)
);

COMMIT;