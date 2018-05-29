
-- select column_name || ', ' from user_tab_columns where table_name like '%IGC_SOURCE_DATA%';
-- select column_name || ', ' from user_tab_columns where table_name like '%DATA_LOGGER%';


-- select * from DATA_LOGGER;
TRUNCATE table FULLY_EXTRACTED_IGC;

insert into FULLY_EXTRACTED_IGC (
  igc_ID,
  TIME_OF_LOG,
  LATITUDE,
  LONGITUDE,
  SATELLITE_COVERAGE,
  PRESSURE_ALTITUDE,
  GPS_ALTITUDE,
  FLIGHt_ID,
  FLIGHT_DATE,
  GLIDER_REGNO
) (SELECT
       igc.ID,
       igc.TIME_OF_LOG,
       igc.LATITUDE,
       igc.LONGITUDE,
       igc.SATELLITE_COVERAGE,
       igc.PRESSURE_ALTITUDE,
       igc.GPS_ALTITUDE,
       igc.FLIGHt_ID,
       log.FLIGHT_DATE,
       log.GLIDER_REGNO
   FROM IGC_SOURCE_DATA igc
     left JOIN Data_Logger_Source log ON
                            igc.FLIGHt_ID = log.ID
               WHERE log.ID NOT IN (SELECT SURR_KEY_FLIGHT
                                    FROM D_FLIGHT) -- this makes sure that we only extract the new ones
);

COMMIT ;