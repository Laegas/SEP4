
select count(*) from IGC_Source_Data;

select count(*) from DATA_LOGGER;

select * from DATA_LOGGER;

select * from LOAD_F_IGC_LOGGER;

select surr_key_flight from D_FLIGHT where
  START_DATE = (select to_Date( (DAY || '.' || MONTH || '.' || YEAR), 'DD.MM.YYYY') from D_DATE
  where
  d_date.year = extract(year from START_DATE) AND
  d_date.month= extract(month from START_DATE) AND
  d_date.day = extract(day from START_DATE));

select *from D_FLIGHT;

select * from D_DATE where d_date.year = extract(year from (select distinct START_DATE from D_FLIGHT));
