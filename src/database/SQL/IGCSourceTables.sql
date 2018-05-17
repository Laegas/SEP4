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
  nomaxvalue


create sequence IGC_Source_Data_ID
  start with 1
  increment by 1
  cache 100
  nomaxvalue

create table Data_Logger
(
    id int primary key,
    gliderRegNo varchar(10),
    flightDate date
)

create table IGC_Source_Data
(
   id int primary key,
   timeOfLog timestamp,
   latitude varchar(7),
   longitude varchar(8),
   sateliteCoverage char(1),
   pressureAltitude int,
   GPSAltitude int,
   fligthID references Data_Logger(id)
)

COMMIT ;