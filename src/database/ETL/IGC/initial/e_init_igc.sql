-- BEGIN
--   EXECUTE IMMEDIATE 'drop table fully_extracted_igc CASCADE CONSTRAINTS purge';
--   EXCEPTION
--   WHEN OTHERS THEN
--   IF SQLCODE != -942 THEN
--     RAISE;
--   END IF;
-- END;
-- /




create table fully_extracted_igc as
  (select
  igc.ID,
  igc.RECORDTYPE,
  igc.TIMEOFLOG,
  igc.LATITUDE,
  igc.LONGITUDE,
  igc.SATELITECOVERAGE,
  igc.PRESSUREALTITUDE,
  igc.GPSALTITUDE,
  igc.FLIGTHID,
  log.FLIGHTDATE
from IGC_SOURCE_DATA igc
join data_logger log ON
  igc.FLIGTHID = log.ID
  where 1 = 0)
;
