-------- INIT FLIGHTS Load ------
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE flights_to_load';
   EXECUTE IMMEDIATE 'DROP TABLE flights_to_load_with_surr_key';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
create table flights_to_load as (select * from FLAGGED_FOR_DUPLICATED_INITIALS where 1 = 0);
alter table flights_to_load add (duration int);
create table flights_to_load_with_surr_key as (select * from flights_to_load);

alter table flights_to_load_with_surr_key add (
  id_launchtime int default null,
  id_launchdate int default null,
  id_landtime int default null,
  id_landdate int default null,
  id_flight int default null
);

alter table flights_to_load_with_surr_key drop (
  LAUNCHAEROTOW,
  LAUNCHWINCH,
  LAUNCHSELFLAUNCH,
  CABLEBREAK
);

------------INIT MEMBER Load-----------
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE MEMBER_TO_LOAD   ';
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
CREATE TABLE MEMBER_TO_LOAD as (SELECT * FROM fixed_status_member where 1 = 0);
CREATE sequence d_member_id
  start with 1
  increment by 1
  cache 100
  noMaxValue
  ;