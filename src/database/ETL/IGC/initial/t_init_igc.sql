BEGIN
  EXECUTE IMMEDIATE 'drop table TRANSFORM_IGC_EMPTY_GLIDER_REGNO CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/



create table load_f_igc_logger as select * from F_IGC_LOG where 1 = 0;
create table load_d_GLIDER as select * from D_GLIDER where 1 = 0;
create table load_D_flight as select * from D_FLIGHT where 1 = 0;

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
)