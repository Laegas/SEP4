BEGIN DROP_TABLE('Data_Logger_Source'); END;
/
BEGIN DROP_TABLE('IGC_Source_Data'); END;
/
BEGIN DROP_SEQUENCE('data_Logger_ID'); END;
/
BEGIN DROP_SEQUENCE('IGC_Source_Data_ID'); END;
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
   flight_ID references Data_Logger_Source(id)
);

COMMIT;