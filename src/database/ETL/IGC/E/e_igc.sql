
-- select column_name || ', ' from user_tab_columns where table_name like '%IGC_SOURCE_DATA%';
-- select column_name || ', ' from user_tab_columns where table_name like '%DATA_LOGGER%';


TRUNCATE table FULLY_EXTRACTED_IGC;
insert into FULLY_EXTRACTED_IGC (
  ID,
  TIME_OF_LOG,
  LATITUDE,
  LONGITUDE,
  SATELITE_COVERAGE,
  PRESSURE_ALTITUDE,
  GPS_ALTITUDE,
  FLIGTH_ID,
  FLIGHT_DATE,
  GLIDER_REGNO
) (select
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
      igc.FLIGTHID = log.ID
  where log.ID not in (Select id from D_FLIGHT) -- this makes sure that we only extract the new ones
  )
;


