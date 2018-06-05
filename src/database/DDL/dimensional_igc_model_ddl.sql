------ Static D_Date DDL and populating ------
BEGIN DROP_TABLE('D_date'); END;
/

CREATE TABLE D_date(
  id_date int primary key,
  day varchar2(2),
  month varchar2(2),
  year varchar2(4),
  day_of_week varchar2(1),
  quarter varchar2(1),
  unique(day, month, year)
);

--create index date_index on d_date (day,month,year);
create index date_day_index on d_date (day);
create index date_month_index on d_date (month);
create index date_year_index on d_date (year);


declare

  oneFineDay interval day to second := to_dsInterval ('001 00:00:00');
  firstDate date := trunc(to_date('1900-01-01','YYYY-MM-DD HH24:MI:SS'), 'DAY');

begin

  for i in 1..(366*200) loop

    insert into d_date(id_date, day, month, year, day_of_week, quarter)
    values (
      i,
      to_char(extract(day from firstDate)),
      to_char(extract(month from firstDate)),
      to_char(extract(year from firstDate)),
      1 + TRUNC (to_date(firstDate)) - TRUNC (to_date(firstDate), 'IW'),
      to_char (to_date(firstDate), 'Q')
    );

    firstDate := firstDate + oneFineDay;

  end loop;

end;
/

------ Static D_Time DDL and populating ------
BEGIN DROP_TABLE('d_time'); END;
/

--insert values for date not found
INSERT INTO D_DATE (id_date, day, month, year, day_of_week, quarter) VALUES (-1, 'NA', 'NA','NAN', 'N', 'N');
--insert values for end of time date
INSERT INTO D_DATE (id_date, day, month, year, day_of_week, quarter) VALUES (0, 31,12,9999,'7','4');

--dimension time creation and insert
create table d_time(
  id_time int primary key,
  hour varchar2(2),
  minute varchar2(2),
  second varchar2(2)
);

create index time_index on d_time (hour,minute,second);
create index time_minute_index on d_time (minute);
create index time_hour_index on d_time (hour);
create index time_second_index on d_time (second);


truncate table d_time;

declare
  one_second interval day to second :=  to_dsinterval('000 00:00:01');
  start_date date := trunc(to_date(sysdate, 'YYYY-MM-DD HH24:MI:SS'), 'DAY');

begin
  for i in 1..(60*60*24) loop
    insert into d_time (id_time, hour, minute, second) values
      (i,
       TO_CHAR(start_date,'HH24'),
       TO_CHAR(start_date,'MI'),
       TO_CHAR(start_date, 'SS'));
    start_date := start_date + one_second;
  end loop;
end;
/
--insert row for unknown time
insert into D_TIME(id_time, hour, minute, second) VALUES (-1,'NA','NA','NA');

BEGIN DROP_TABLE('D_GLIDER'); END;
/
BEGIN DROP_TABLE('D_FLIGHT'); END;
/
BEGIN DROP_TABLE('F_IGC_LOG'); END;
/

-------------------------------------------------------
create table D_GLIDER (
  surr_key_glider int PRIMARY KEY,
  glider_id varchar2(10),
  valid_from date,
  valid_to date
);

create table D_FLIGHT(
  surr_key_flight int PRIMARY KEY,
  surr_key_glider int references D_GLIDER(surr_key_glider),
  id_start_date int references D_DATE(ID_DATE)
);

CREATE table F_IGC_LOG(
  SURR_KEY_LOG int,
  surr_key_flight int REFERENCES D_FLIGHT(surr_key_flight),
  id_time int references D_TIME(ID_TIME),
  lat_north char(7),
  long_east char(8),
  press_altitude INT,
  gps_altitude INT,
  gps_ok char(1),
  closest_airport INT references D_AIRPORT(SURR_KEY_AIRPORT),
  PRIMARY KEY (surr_key_flight, id_time)
);

COMMIT;
