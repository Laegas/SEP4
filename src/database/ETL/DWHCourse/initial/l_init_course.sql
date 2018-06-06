-------- INIT FLIGHTS Load ------
BEGIN drop_table('flights_to_load_with_surr_key'); END;
/
BEGIN drop_table('flights_to_load'); END;
/

create table flights_to_load as (select * from FLAGGED_FOR_DUPLICATED_INITIALS where 1 = 0);
-- adding column for calculated duration of flight
alter table flights_to_load add (duration int);
create table flights_to_load_with_surr_key as (select * from flights_to_load);

alter table flights_to_load_with_surr_key add ( -- adding columns mostly for key lookup
  surr_key_flight int default null,
  id_launchdate int default null,
  id_launchtime int default null,
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
BEGIN drop_table('MEMBER_TO_LOAD'); END;
/
BEGIN drop_sequence('d_member_id'); END;
/

CREATE TABLE MEMBER_TO_LOAD as (SELECT * FROM fixed_status_member where 1 = 0);

CREATE sequence d_member_id
  start with 1
  increment by 1
  cache 100
  noMaxValue
  ;

COMMIT;