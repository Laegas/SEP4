BEGIN
  EXECUTE IMMEDIATE 'drop table Data_Logger CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table IGC_Source_Data CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/

BEGIN
  EXECUTE IMMEDIATE 'drop sequence data_Logger_ID';
  EXECUTE IMMEDIATE 'drop sequence IGC_Source_Data_ID';
EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;
/

create sequence data_Logger_ID
  start with 1
  increment by 1
  cache 100
  nomaxvalue;


create sequence IGC_Source_Data_ID
  start with 1
  increment by 1
  cache 100
  nomaxvalue;

create table Data_Logger_Source
(
    id int primary key,
    glider_RegNo varchar2(10),
    flight_Date date
);

create table IGC_Source_Data
(
   id int primary key,
   time_Of_Log timestamp,
   latitude varchar2(7),
   longitude varchar2(8),
   satellite_Coverage char(1),
   pressure_Altitude int,
   GPS_Altitude int,
   flight_ID references Data_Logger(id)
);

COMMIT;

select count(*) from IGC_Source_Data;