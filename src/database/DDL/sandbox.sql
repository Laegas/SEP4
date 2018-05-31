
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

select min(TEMPERATURE), max(TEMPERATURE) from F_WEATHER_RECORD;


select * from user_constraints where constraint_Name ='SYS_C0012669';
select * from user_constraints where constraint_Name ='SYS_C0012666';


