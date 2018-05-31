
-- select column_name || ', ' from user_tab_columns where table_name like '%IGC_SOURCE_DATA%';
-- select column_name || ', ' from user_tab_columns where table_name like '%DATA_LOGGER%';


-- select * from DATA_LOGGER;
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
  GLIDER_REGNO,
  OPERATION_CODE
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
       log.GLIDER_REGNO,
       'INS'
   FROM IGC_SOURCE_DATA igc
     left JOIN Data_Logger_Source log ON igc.FLIGHt_ID = log.ID
   WHERE log.ID NOT IN (SELECT SURR_KEY_FLIGHT FROM D_FLIGHT) -- this makes sure that we only extract the new ones
    AND  log.FLIGHT_DATE > (select lastdate FROM last_Date_Of_IGC_Extraction)
  AND
   igc.ID in
  (SELECT igc.ID from IGC_SOURCE_DATA
   minus
   SELECT igc.ID from IGC_YESTERDAY
  )
);
select * from IGC_SOURCE_DATA;
select * from IGC_YESTERDAY;
select * from D_FLIGHT;
select * from FULLY_EXTRACTED_IGC;
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
  GLIDER_REGNO,
  OPERATION_CODE
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
     log.GLIDER_REGNO,
     'DEL'
   FROM IGC_SOURCE_DATA igc
     left JOIN Data_Logger_Source log ON igc.FLIGHt_ID = log.ID
   WHERE log.ID NOT IN (SELECT SURR_KEY_FLIGHT FROM D_FLIGHT) -- this makes sure that we only extract the new ones
         AND  log.FLIGHT_DATE > (select lastdate FROM last_Date_Of_IGC_Extraction)
         AND igc.ID in
         (SELECT igc.ID from IGC_YESTERDAY
          minus
          SELECT igc.ID from IGC_SOURCE_DATA
         )
);

insert into FULLY_EXTRACTED_IGC (
  IGC_ID,
  TIME_OF_LOG,
  LATITUDE,
  LONGITUDE,
  SATELLITE_COVERAGE,
  PRESSURE_ALTITUDE,
  GPS_ALTITUDE,
  FLIGHt_ID,
  FLIGHT_DATE,
  GLIDER_REGNO,
  OPERATION_CODE
) (SELECT
     m.ID,
     m.TIME_OF_LOG,
     m.LATITUDE,
     m.LONGITUDE,
     m.SATELLITE_COVERAGE,
     m.PRESSURE_ALTITUDE,
     m.GPS_ALTITUDE,
     m.FLIGHT_ID,
     log.FLIGHT_DATE,
     log.GLIDER_REGNO,
     OPERATION_CODE
   FROM ((select igc.ID,
            igc.TIME_OF_LOG,
            igc.LATITUDE,
            igc.LONGITUDE,
            igc.SATELLITE_COVERAGE,
            igc.PRESSURE_ALTITUDE,
            igc.GPS_ALTITUDE,
            igc.FLIGHT_ID,
            'CHG' as OPERATION_CODE from IGC_SOURCE_DATA igc)
         minus
         (select yigc.ID,
            yigc.TIME_OF_LOG,
            yigc.LATITUDE,
            yigc.LONGITUDE,
            yigc.SATELLITE_COVERAGE,
            yigc.PRESSURE_ALTITUDE,
            yigc.GPS_ALTITUDE,
            yigc.FLIGHt_ID,
            'CHG' as OPERATION_CODE from IGC_YESTERDAY yigc)
   ) m
     left JOIN Data_Logger_Source log ON m.FLIGHt_ID = log.ID
   WHERE m.ID NOT IN (SELECT SURR_KEY_FLIGHT FROM D_FLIGHT) -- this makes sure that we only extract the new ones
         AND  log.FLIGHT_DATE > (select lastdate FROM last_Date_Of_IGC_Extraction)
  AND m.ID not in
(SELECT IGC_ID from FULLY_EXTRACTED_IGC where OPERATION_CODE = 'INS'));

UPDATE last_Date_Of_IGC_Extraction set lastDate = CURRENT_DATE;

COMMIT ;