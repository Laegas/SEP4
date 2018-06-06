BEGIN DROP_TABLE('weather_record'); END;
/
BEGIN DROP_TABLE('airport'); END;
/

BEGIN DROP_SEQUENCE('airport_id'); END;
/
BEGIN DROP_SEQUENCE('weather_record_id'); END;
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
  ICAO_airport_Code varchar2(4) primary key,
  latitude varchar2(7),
  longitude varchar2(8),
  countryName varchar2(100),
  airportName varchar2(100),
  altitude int,
  wmo_index VARCHAR2(5)
);

create table weather_record
(
  id int primary key,
  ICAO_airport_code varchar2(4) references airport(ICAO_airport_code),
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

COMMIT;
-- select count (*) from weather_record;
