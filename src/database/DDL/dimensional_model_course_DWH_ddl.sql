-------- D_Member DDL --------
BEGIN DROP_TABLE('D_MEMBER'); END;
/
BEGIN DROP_SEQUENCE('d_member_id'); END;
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
  Name varchar2(50) not null,
  Address varchar2(50) not null,
  ZIPCode number(4,0) not null,
  DateBorn date not null,
  DateJoined date not null,
  DateLeft date,
  OwnsPlaneReg char(3) not null,
  Sex char(1) not null,
  Club varchar2(50) not null,
  Status varchar2(255) not null,
  valid_from date not null,
  valid_to date not null,
  primary key (Member_ID)
);
insert into D_MEMBER(
  Member_ID,
  MemberNo ,
  Initials,
  Name ,
  Address ,
  ZIPCode ,
  DateBorn,
  DateJoined ,
  DateLeft ,
  OwnsPlaneReg ,
  Sex  ,
  Club ,
  Status ,
  valid_from ,
  valid_to)
VALUES (
  -1,
  0,
  'aaaa',
  'name',
  'Address',
  0000,
  TO_DATE('0001/01/01', 'YYYY/MM/DD'),
  TO_DATE('0001/01/01', 'YYYY/MM/DD'),
  TO_DATE('0001/01/01', 'YYYY/MM/DD'),
  'aaa',
  'M',
  'club',
  'BLA',
  TO_DATE('0001/01/01', 'YYYY/MM/DD'),
  TO_DATE('0001/01/01', 'YYYY/MM/DD')
);

-------- F_Duration and B_Flight_Member table ddl --------
BEGIN DROP_TABLE('F_Duration'); END;
/
BEGIN DROP_TABLE('B_FLIGHT_MEMBER'); END;
/
BEGIN DROP_SEQUENCE('SEQ_ID_B_FLIGHT_MEMBER'); END;
/
BEGIN DROP_SEQUENCE('seq_id_flights'); END;
/

create sequence SEQ_ID_B_FLIGHT_MEMBER
  start with 1
  increment by 1
  cache 100
  nomaxvalue
;

create table B_FLIGHT_MEMBER (
  ID_GROUP int,
  ID_MEMBER int references d_member(member_id),
  weiGHt decimal(2,1) not null,
  primary key (ID_GROUP, ID_MEMBER)
);

--nonexistent - using for insert from thermal side instead of member side.

insert into B_FLIGHT_MEMBER(
  ID_GROUP, ID_MEMBER, weight
) VALUES (-1, -1, -1);

-----F_Duration was previously F_Flight-----
create table F_Duration(
  surr_key_flight int references D_FLIGHT(surr_key_flight),
  id_group int,
  id_launch_time int references D_TIME(ID_TIME),
  id_launch_date int references D_DATE(ID_DATE),
  id_land_time int references D_TIME(ID_TIME),
  id_land_date int references D_DATE(ID_DATE),
  duration int not null,
  primary key(surr_key_flight, id_group, id_launch_time, id_launch_date, id_land_time, id_land_date)
);

create sequence seq_id_flights
  start with 1
  increment by 1
  cache 100
  nomaxvalue
;

prompt DIMENSIONAL MODEL COURSE DWH DDL FINISHED.

COMMIT;
