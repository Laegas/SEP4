
select count(*) from IGC_Source_Data;

select count(*) from DATA_LOGGER;

select * from DATA_LOGGER;

select * from LOAD_F_IGC_LOGGER;

select count(*) from F_IGC_LOG;

SELECT DISTINCT hour
FROM WEATHER_RECORD;

select * from f_weather_record;

select w_date,w_time,wind_direction,wind_speed,WIND_DIRECTION_FROM,WIND_DIRECTION_TO,temperature,dew_point,airport_code from f_weather_record where w_date = TO_DATE(?/?/?, 'dd/mm/yyyy');
