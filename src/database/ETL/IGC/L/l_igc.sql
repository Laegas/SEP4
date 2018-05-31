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
          FROM TRANSFORM_IGC_EMPTY_GLIDER_REGNO)
    WHERE GLIDER_REGNO not in (select GLIDER_ID from D_GLIDER)
);

-- inserting new flights into d_flights
insert into D_FLIGHT (
    SURR_KEY_FLIGHT,
    ID_START_DATE,
    SURR_KEY_GLIDER
) (select distinct t.flight_id,
       (select id_date from d_date where
         d_date.year = extract(year from t.FLIGHT_DATE) AND
         d_date.month= extract(month from t.FLIGHT_DATE) AND
         d_date.day = extract(day from t.FLIGHT_DATE)),
      g.SURR_KEY_GLIDER
    from TRANSFORM_IGC_EMPTY_GLIDER_REGNO t
      join D_GLIDER g
      on (t.GLIDER_REGNO = g.GLIDER_ID)
    where flight_id not in (select SURR_KEY_FLIGHT from D_FLIGHT)
          AND g.VALID_FROM<=sysdate AND g.VALID_TO>sysdate
)
;

--insert the flight surr key and it's starting date into the F_Duration table (linking DWHs)
insert into F_DURATION (
    SURR_KEY_FLIGHT,
    ID_GROUP,
    ID_MEMBER,
    ID_LAUNCH_TIME,
    ID_LAUNCH_DATE,
    ID_LAND_TIME,
    ID_LAND_DATE,
    DURATION
) (select
    SURR_KEY_FLIGHT,
    -1,
    -1,
    -1,
    ID_START_DATE,
    -1,
    -1,
    -1
   from D_FLIGHT
   where SURR_KEY_FLIGHT not in (select SURR_KEY_FLIGHT from F_DURATION)
);

-- select * from FULLY_EXTRACTED_IGC;
-- now inserting all new f_igc_log
create sequence test_id_time_problem
  start with 1
  increment by 1;

insert into F_IGC_LOG (
    SURR_KEY_LOG,
    SURR_KEY_FLIGHT,
    ID_TIME,
    LAT_NORTH,
    LONG_EAST,
    PRESS_ALTITUDE,
    GPS_ALTITUDE,
    GPS_OK
) (select
    IGC_ID,
    FLIGHT_ID,
   (
     case when (select id_time
                from d_time
                where
                  hour || minute || second = (extract(hour from t.TIME_OF_LOG))
                                             || (extract(minute from t.TIME_OF_LOG))
                                             || (to_char(t.TIME_OF_LOG, 'SS'))) is NULL
       then test_id_time_problem.nextval
     else
       (select id_time
        from d_time
        where
          hour || minute || second = (extract(hour from t.TIME_OF_LOG))
                                     || (extract(minute from t.TIME_OF_LOG))
                                     || (to_char(t.TIME_OF_LOG, 'SS')))
     end
   ),
    LATITUDE,
    LONGITUDE,
    PRESSURE_ALTITUDE,
    GPS_ALTITUDE,
    SATELLITE_COVERAGE
  from TRANSFORM_IGC_EMPTY_GLIDER_REGNO t
);--------cannot insert null

select * from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where TIME_OF_LOG = '';
select * from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 12;
--finding the Time ID!!!!!!!!!!!!
select * from d_time where
  hour||':'||minute||':'||second = (extract(hour from (select TIME_OF_LOG from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 50))
  ||':'||(extract(minute from (select TIME_OF_LOG from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 50)))
  ||':'|| to_char((select TIME_OF_LOG from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 50),'SS'));

select * from TRANSFORM_IGC_EMPTY_GLIDER_REGNO order by TIME_OF_LOG asc;
COMMIT ;
