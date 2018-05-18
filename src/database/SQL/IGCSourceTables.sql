BEGIN
  EXECUTE IMMEDIATE 'drop table Data_Logger CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table IGC_Source_Data CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;

BEGIN
  EXECUTE IMMEDIATE 'drop sequence data_Logger_ID';
  EXECUTE IMMEDIATE 'drop sequence IGC_Source_Data_ID';
EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;


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

create table Data_Logger
(
    id int primary key,
    glider_RegNo varchar(10),
    flight_Date date
);

create table IGC_Source_Data
(
   id int primary key,
   time_Of_Log timestamp,
   latitude varchar(8),
   longitude varchar(7),
   satelite_Coverage char(1),
   pressure_Altitude int,
   GPS_Altitude int,
   fligth_ID references Data_Logger(id)
);

COMMIT;