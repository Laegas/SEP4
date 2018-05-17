
-- select column_name || ', ' from user_tab_columns where table_name like '%IGC_SOURCE_DATA%';
-- select column_name || ', ' from user_tab_columns where table_name like '%DATA_LOGGER%';


insert into FULLY_EXTRACTED_IGC
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
  where log.ID not in (Select id from D_FLIGHT) -- this makes sure that we only extract the new ones
  )

;



