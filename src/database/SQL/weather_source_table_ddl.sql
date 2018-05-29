BEGIN
  EXECUTE IMMEDIATE 'drop table weather_record CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table airport CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'drop sequence airport_id';
  EXECUTE IMMEDIATE 'drop sequence weather_record_id';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;
/

create sequence airport_id
  start with 1
  increment by 1
  cache 100
  nomaxvalue;

create sequence weather_record_id
  start with 1
  increment by 1
  cache 100
  nomaxvalue;


create table airport
(
   id int primary key,
   ICAO_airport_Code varchar(4),
   latitude varchar(7),
   longitude varchar(8),
   countryName varchar(100),
   airportName varchar(100),
   altitude int
);

create table weather_record
(
  id int primary key,
  ICAO_airport_code varchar(4),
  airport_id int references airport(aiport_id),
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
