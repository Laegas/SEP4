drop sequence data_Logger_ID;
drop sequence IGC_Source_Data_ID;
drop table Data_Logger;
drop table IGC_Source_Data;

create sequence data_Logger_ID
  start with 1
  increment by 1
  /

create seqeuence IGC_Source_Data_ID
  start with 1
  increment by 1
  /

create table Data_Logger
(
    id int primary key,
    flightDate date
)/

create table IGC_Source_Data
(
   id int primary key,
   recordType char(1),
   timeOfLog timestamp,
   latitude int,
   longitude int,
   sateliteCoverage char(1),
   pressureAltitude int,
   GPSAltitude int,
   fligthID references Data_Logger(id)
)/