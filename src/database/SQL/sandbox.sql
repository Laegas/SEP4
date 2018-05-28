
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

select D_IGC_WEATHER.SURR_KEY_IGC_WEATHER from D_IGC_WEATHER
join F_WEATHER_RECORD
  on (F_WEATHER_RECORD.SURR_KEY_IGC_WEATHER = D_IGC_WEATHER.SURR_KEY_IGC_WEATHER)
join D_flight
  on (D_flight.START_DATE = F_WEATHER_RECORD.W_DATE);

