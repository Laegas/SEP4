BEGIN
  EXECUTE IMMEDIATE 'drop table fully_extracted_igc CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table last_Date_Of_IGC_Extraction CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table IGC_YESTERDAY CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/

CREATE TABLE last_Date_Of_IGC_Extraction (lastDate date);
INSERT INTO last_Date_Of_IGC_Extraction (lastDate) VALUES (to_date('0001-01-01', 'YYYY-MM-DD'));

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

CREATE TABLE IGC_YESTERDAY as (SELECT * FROM IGC_SOURCE_DATA where 1 = 0);

COMMIT;