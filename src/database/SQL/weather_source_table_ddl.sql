BEGIN
  EXECUTE IMMEDIATE 'drop table weather_record CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
BEGIN
  EXECUTE IMMEDIATE 'drop sequence weather_record_id';
EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;
/
create sequence weather_record_id
  start with 1
  increment by 1
  cache 100
  nomaxvalue;

create table weather_source
(
  w_id int,
  ICAO_airport_code varchar(4) references airport_source(ICAO_Airport_Code),
  wind_direction int,
  wind_speed int,
  wind_direction_from int,
  wind_direction_to int,
  temperature number(5,2),
  dew_point number(5,2),
  the_date date,
  hour int,
  minute int
);

create table airport_source(
  ICAO_Airport_Code varchar(4) primary key,
  altitude int,
  airport_longitude varchar(8),
  airport_latitude varchar(7),
  airport_name varchar(50),
  country_name varchar(100),
  WMO_Index varchar(5),
  id_valid_from int references D_DATE(ID_DATE),
  id_valid_to int references D_DATE(ID_DATE)
);

COMMIT;
-- select count (*) from weather_record;
