insert into TRANSFORM_IGC_EMPTY_GLIDER_REGNO (
        LONGITUDE,
        LATITUDE,
        TIME_OF_LOG,
        GPS_ALTITUDE,
        SATELLITE_COVERAGE,
        IGC_ID,
        PRESSURE_ALTITUDE,
        FLIGHT_DATE,
        FLIGHT_ID,
        GLIDER_REGNO
  )
  (
    SELECT
        LONGITUDE,
        LATITUDE,
        TIME_OF_LOG,
        GPS_ALTITUDE,
        SATELLITE_COVERAGE,
        IGC_ID,
        PRESSURE_ALTITUDE,
        FLIGHT_DATE,
        FLIGHT_ID,
       case when GLIDER_REGNO IS NULL OR GLIDER_REGNO = '          ' then 'unknown'
                else GLIDER_REGNO
       end
    FROM FULLY_EXTRACTED_IGC
        where IGC_ID not IN (select IGC_ID from TRANSFORM_IGC_EMPTY_GLIDER_REGNO)
  );
COMMIT ;

