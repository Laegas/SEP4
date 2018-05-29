truncate table AIRPORTS_YESTERDAY;
insert into AIRPORTS_YESTERDAY (select * from AIRPORT);