BEGIN
  EXECUTE IMMEDIATE 'drop table fully_extracted_igc CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/




create table fully_extracted_igc as
  (select
  igc.ID,
  igc.TIME_OF_LOG,
  igc.LATITUDE,
  igc.LONGITUDE,
  igc.SATELITE_COVERAGE,
  igc.PRESSURE_ALTITUDE,
  igc.GPS_ALTITUDE,
  igc.FLIGTH_ID,
  log.FLIGHT_DATE,
  log.GLIDER_REGNO
from IGC_SOURCE_DATA igc
join data_logger log ON
  igc.FLIGTH_ID = log.ID
  where 1 = 0)
;
