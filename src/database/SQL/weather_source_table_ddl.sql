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

create table weather_record
(
  w_id int,
  ICAO_airport_code varchar(4),
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


-- select count (*) from weather_record;
