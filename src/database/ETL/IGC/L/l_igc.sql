-- generating cloumns for dimentional tables
-- select column_name || ', ' from user_tab_cols where table_name like '%F_IGC%';
-- select column_name || ', ' from user_tab_cols where table_name like '%D_FLIGHT%';
-- select column_name || ', ' from user_tab_cols where table_name like '%D_GLIDER%';


-- select column_name || ', ' from user_tab_cols where table_name like '%FULLY_EXTRACTED_IGC%';


-- insert into dimentions
-- inserting into D_glider
insert into D_GLIDER (
    SURR_KEY_GLIDER,
    GLIDER_ID
) (
    SELECT
        GLIDER_SURR_KEY.nextval,
        GLIDER_REGNO
    FROM (SELECT DISTINCT GLIDER_REGNO
          FROM FULLY_EXTRACTED_IGC)
);

-- inserting new flights into d_flights
insert into D_FLIGHT (
    SURR_KEY_FLIGHT,
    START_DATE
) (select flight_id,
    flight_date from
    (select distinct flight_id,
    FLIGHT_DATE from FULLY_EXTRACTED_IGC
    where flight_id not in (select SURR_KEY_FLIGHT from D_FLIGHT))
);

-- select * from FULLY_EXTRACTED_IGC;
-- now inserting all new f_igc_log
insert into F_IGC_LOG (
    SURR_KEY_LOG,
    SURR_KEY_FLIGHT,
    SURR_KEY_GLIDER,
    TIME,
    LAT_NORTH,
    LONG_EAST,
    PRESS_ALTITUDE,
    GPS_ALTITUDE,
    GPS_OK
) (select
    igc_ID,
    FLIGht_ID,
    (select SURR_KEY_GLIDER from d_glider g where g.GLIDER_ID = GLIDER_REGNO) as surr_key_glider,
    TIME_OF_LOG,
    LATITUDE,
    LONGITUDE,
    PRESSURE_ALTITUDE,
    GPS_ALTITUDE,
    SATELITE_COVERAGE
  from FULLY_EXTRACTED_IGC
    where igc_id not IN (select SURR_KEY_LOG from F_IGC_LOG)
);




