create sequence dataLoggerID
  start with 1
  increment by 1
;

create table DataLogger
(
    id int primary key,
    flightDate date
);

create table IGCSourceData
(
   recordType char(1),
   timeOfLog timestamp,
   latitude int,
   longitude int,
   sateliteCoverage char(1),
   pressureAltitude int,
   GPSAltitude int,
   fligthID references DataLogger(id) primary KEY
);