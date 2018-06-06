BEGIN DROP_TABLE('load_f_igc_logger'); END;
/
BEGIN DROP_TABLE('load_d_glider'); END;
/
BEGIN DROP_TABLE('load_d_flight'); END;
/
BEGIN DROP_TABLE('TRANSFORM_IGC_EMPTY_GLIDER_REGNO'); END;
/

create table load_f_igc_logger as select * from F_IGC_LOG where 1 = 0;
create table load_d_glider as select * from D_GLIDER where 1 = 0;
create table load_d_flight as select * from D_FLIGHT where 1 = 0;

create table TRANSFORM_IGC_EMPTY_GLIDER_REGNO as
(select
  IGC_ID,
  TIME_OF_LOG,
  LATITUDE,
  LONGITUDE,
  SATELLITE_COVERAGE,
  PRESSURE_ALTITUDE,
  GPS_ALTITUDE,
  FLIGHT_ID,
  FLIGHT_DATE,
  GLIDER_REGNO
  FROM FULLY_EXTRACTED_IGC
  where 1=0
);

COMMIT;