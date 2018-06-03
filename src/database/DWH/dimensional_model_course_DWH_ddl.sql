-------- D_Member DDL --------
------ Static D_Date DDL and populating ------
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE D_date CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
CREATE TABLE D_date(
  id_date int primary key,
  day varchar2(2) not null,
  month varchar2(2) not null,
  year varchar2(4) not null,
  day_of_week varchar2(1) not null,
  quarter varchar2(1) not null,
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
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE d_time CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
--insert values for date not found
INSERT INTO D_DATE (id_date, day, month, year, day_of_week, quarter) VALUES (-1, 'NA', 'NA','NAN', 'N', 'N');
--insert values for end of time date
INSERT INTO D_DATE (id_date, day, month, year, day_of_week, quarter) VALUES (0, 31,12,9999,'7','4');

--dimension time creation and insert
create table d_time(
  id_time int primary key,
  hour varchar(2) not null,
  minute varchar(2) not null,
  second varchar(2) not null
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

BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE D_MEMBER CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
BEGIN
  EXECUTE IMMEDIATE 'DROP SEQUENCE d_member_id';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;
/

CREATE sequence d_member_id
  start with 1
  increment by 1
  cache 100
  noMaxValue
;

create table D_MEMBER(
  Member_ID int,
  MemberNo number(6,0) not null,
  Initials char(04) not null,
  Name varchar(50) not null,
  Address varchar(50) not null,
  ZIPCode number(4,0) not null,
  DateBorn date not null,
  DateJoined date not null,
  DateLeft date not null,
  OwnsPlaneReg char(3) not null,
  Sex char(1) not null,
  Club varchar(50) not null,
  Status varchar(255) not null,
  valid_from date not null,
  valid_to date not null,
  primary key (Member_ID)
);

-------- F_Flight and B_Flight_Member table ddl --------
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE F_Flight CASCADE CONSTRAINTS purge';
   EXECUTE IMMEDIATE 'DROP TABLE B_FLIGHT_MEMBER CASCADE CONSTRAINTS purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

BEGIN
   EXECUTE IMMEDIATE 'DROP SEQUENCE seq_id_flights';
  EXECUTE IMMEDIATE 'DROP SEQUENCE SEQ_ID_B_FLIGHT_MEMBER';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
END;
/
--describe flights_to_load_with_surr_key;

create sequence SEQ_ID_B_FLIGHT_MEMBER
  start with 1
  increment by 1
  cache 100
  nomaxvalue
;

create table B_FLIGHT_MEMBER (
  ID_GROUP int not null ,
  ID_MEMBER int references D_MEMBER(Member_ID),
  weiGHt decimal(2,1) not null,
  primary key(id_group, ID_MEMBER)
);

--nonexistent - using for insert from thermal side instead of member side.
insert into D_MEMBER(
  Member_ID
) VALUES (-1);
insert into B_FLIGHT_MEMBER(
  ID_GROUP, ID_MEMBER, weight
) VALUES (-1, -1, -1);

-----F_Flight was previously F_Flight-----
create table F_Flight(
  surr_key_flight int references D_FLIGHT(surr_key_flight),
  id_group int not null,
  id_member not null,
  id_launch_time int references D_TIME(ID_TIME),
  id_launch_date int references D_DATE(ID_DATE),
  id_land_time int references D_TIME(ID_TIME),
  id_land_date int references D_DATE(ID_DATE),
  duration int not null,
  FOREIGN KEY  (id_group, id_member) REFERENCES B_FLIGHT_MEMBER (id_group, id_member),
  primary key(surr_key_flight, id_group, id_member, id_launch_time, id_launch_date, id_land_time, id_land_date)
);

create sequence seq_id_flights
  start with 1
  increment by 1
  cache 100
  nomaxvalue
;