
select count(*) from IGC_Source_Data;

select count(*) from DATA_LOGGER;

select * from DATA_LOGGER;

select * from LOAD_F_IGC_LOGGER;

select count(*) from F_IGC_LOG;

SELECT DISTINCT hour
FROM WEATHER_RECORD;

select count(distinct ICAO_AIRPORT_CODE) from WEATHER_RECORD;
