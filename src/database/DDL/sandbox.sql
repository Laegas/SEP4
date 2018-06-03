
select count(*) from IGC_Source_Data;

select count(*) from DATA_LOGGER;

select * from DATA_LOGGER;

select * from LOAD_F_IGC_LOGGER;

select ID_DATE from D_Date
where (select to_Date( (DAY || '.' || MONTH || '.' || YEAR), 'DD.MM.YYYY') from D_DATE)
      = (select D_Flight.START_DATE from D_FLIGHT)
     intersect
select id_launch_date from F_DURATION;

select *from D_FLIGHT;



select count(*) from WEATHER_RECORD;
select count(*) from IGC_SOURCE_DATA;
select count(*) from DATA_LOGGER;
select * from DATA_LOGGER;

select fw.SURR_KEY_IGC_WEATHER from F_WEATHER_RECORD fw
     join D_flight df
          on (df.START_DATE = fw.W_DATE)
     join TRANSFORM_IGC_EMPTY_GLIDER_REGNO tr
          on (df.SURR_KEY_FLIGHT = tr.FLIGHT_ID)
where tr.FLIGHT_ID = SURR_KEY_FLIGHT;

select diw.SURR_KEY_IGC_WEATHER from D_IGC_WEATHER diw
  join F_WEATHER_RECORD fw
    on (fw.SURR_KEY_IGC_WEATHER = diw.SURR_KEY_IGC_WEATHER)
  join D_flight df
    on (START_DATE = W_DATE)
  join TRANSFORM_IGC_EMPTY_GLIDER_REGNO tr
    on (df.SURR_KEY_FLIGHT = tr.FLIGHT_ID)
where AIRPORT_CODE = 'EKAH';

select * from F_WEATHER_RECORD where AIRPORT_CODE = 'EKAH';

select * from F_WEATHER_RECORD;
select * from F_WEATHER_RECORD;

select * from F_WEATHER_RECORD;
select * from F_IGC_LOG;
select * from D_GLIDER;
select * from D_AIRPORT;
select * from D_FLIGHT;

select min(TEMPERATURE), max(TEMPERATURE) from F_WEATHER_RECORD;


select * from user_constraints where constraint_Name ='SYS_C0013150';
select * from user_constraints where constraint_Name ='SYS_C0013049';


select * from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where TIME_OF_LOG = '';
select * from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 12;
--finding the Time ID!!!!!!!!!!!!
select * from d_time where
  hour||':'||minute||':'||second = (extract(hour from (select TIME_OF_LOG from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 50))
                                    ||':'||(extract(minute from (select TIME_OF_LOG from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 50)))
                                    ||':'|| to_char((select TIME_OF_LOG from TRANSFORM_IGC_EMPTY_GLIDER_REGNO where IGC_ID = 50),'SS'));

select * from TRANSFORM_IGC_EMPTY_GLIDER_REGNO order by TIME_OF_LOG asc;

DELETE from F_IGC_LOG where 1=1;


select count(*) from TRANSFORM_WEATHER_HOUR_MINUTE_TO_TIME;
select count(*) from WEATHER_RECORD group by HOUR, MINUTE,ICAO_AIRPORT_CODE,THE_DATE;
select count(*)from WEATHER_RECORD;
SELECT COUNT(*) FROM AIRPORT;

select count(*) from FULLY_EXTRACTED_IGC;
SELECT COUNT(*) FROM D_AIRPORT GROUP BY ICAO_AIRPORT_CODE;
SELECT COUNT(*) FROM IGC_SOURCE_DATA;


select * from f_igc_log join d_flight on d_flight.SURR_KEY_FLIGHT = F_IGC_LOG.SURR_KEY_FLIGHT;
select * from f_igc_log where CLOSEST_AIRPORT is not null and closest_airport <> 23 and CLOSEST_AIRPORT <> 18 and CLOSEST_AIRPORT <> 1;

SELECT count(*) from D_AIRPORT join F_WEATHER_RECORD on D_AIRPORT.SURR_KEY_AIRPORT = F_WEATHER_RECORD.SURR_KEY_AIRPORT where D_AIRPORT.SURR_KEY_AIRPORT = 1 or D_AIRPORT.SURR_KEY_AIRPORT = 18 or D_AIRPORT.SURR_KEY_AIRPORT = 23;
select count(*) from F_WEATHER_RECORD;
