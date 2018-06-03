-------- D_Member DDL --------

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
  DateLeft date,
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
   EXECUTE IMMEDIATE 'DROP TABLE F_flight CASCADE CONSTRAINTS purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
BEGIN
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
-----F_Duration was previously F_Flight-----
create table F_FLIGHT(
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