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
  igc.ID as igc_id,
  igc.TIME_OF_LOG,
  igc.LATITUDE,
  igc.LONGITUDE,
  igc.SATELLITE_COVERAGE,
  igc.PRESSURE_ALTITUDE,
  igc.GPS_ALTITUDE,
  igc.FLIGHt_ID,
  log.FLIGHT_DATE,
  log.GLIDER_REGNO
from IGC_SOURCE_DATA igc
join Data_Logger_Source log ON
  igc.FLIGht_ID = log.ID
  where 1 = 0)
;

COMMIT;