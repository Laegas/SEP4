-------- TRANSFORM FLIGHTS------
-------- TRUNCATE ------
truncate table fixed_Land_Launch_Time;
truncate table fixed_pilot_info;
truncate table flagged_for_duplicated_initials;

-- creating table like tohandlelaterflights to save the flights with reversed launch and land time;
-- the cut off duration for expected reversal of duration is duration longer than 2 days

insert into FIXED_LAND_LAUNCH_TIME
(LAUNCHTIME, LANDINGTIME,PLANEREGISTRATION,PILOT1INIT,PILOT2INIT,LAUNCHAEROTOW,
 LAUNCHWINCH,LAUNCHSELFLAUNCH,CABLEBREAK,CROSSCOUNTRYKM,CLUBNAME)
  (
    (SELECT LAUNCHTIME, LANDINGTIME,PLANEREGISTRATION,PILOT1INIT,PILOT2INIT,LAUNCHAEROTOW,
       LAUNCHWINCH,LAUNCHSELFLAUNCH,CABLEBREAK,CROSSCOUNTRYKM,CLUBNAME from TOHANDLELATERFLIGHTS where (LANDINGTIME-launchtime) >= 0 and landingtime <> to_date('31-12-9999','DD-MM-YYYY'))
    union
    (SELECT LANDINGTIME as LAUNCHTIME, LAUNCHTIME as LANDINGTIME,PLANEREGISTRATION,PILOT1INIT,PILOT2INIT,LAUNCHAEROTOW,
       LAUNCHWINCH,LAUNCHSELFLAUNCH,CABLEBREAK,CROSSCOUNTRYKM,CLUBNAME from TOHANDLELATERFLIGHTS where (LANDINGTIME-launchtime) < 0)
    union
    (SELECT LAUNCHTIME, LAUNCHTIME as landingtime,PLANEREGISTRATION,PILOT1INIT,PILOT2INIT,LAUNCHAEROTOW,
       LAUNCHWINCH,LAUNCHSELFLAUNCH,CABLEBREAK,CROSSCOUNTRYKM,CLUBNAME from TOHANDLELATERFLIGHTS where landingtime = to_date('31-12-9999','DD-MM-YYYY'))
  )
;
-- finished validating and fixing the land and launch date

-- validating pilot info, pilot1init is always used
insert into fixed_pilot_info (SELECT LAUNCHTIME, LANDINGTIME,PLANEREGISTRATION,PILOT1INIT,PILOT2INIT,LAUNCHAEROTOW, LAUNCHWINCH,LAUNCHSELFLAUNCH,CABLEBREAK,CROSSCOUNTRYKM,CLUBNAME
                              from FIXED_LAND_LAUNCH_TIME where pilot1init <> '    ')
                             union
                             (SELECT LAUNCHTIME, LANDINGTIME,PLANEREGISTRATION,PILOT2INIT as pilot1init,pilot1init as PILOT2INIT,LAUNCHAEROTOW, LAUNCHWINCH,LAUNCHSELFLAUNCH,CABLEBREAK,CROSSCOUNTRYKM,CLUBNAME
                              from FIXED_LAND_LAUNCH_TIME where pilot1init = '    ' and pilot2init <> '    ' )
;

-- inserting data into flagged_for_dup initials --
insert into flagged_for_duplicated_initials (
  select f.*, d1.pilot1_non_unique_initials, d2.pilot2_non_unique_initials from fixed_pilot_info f
    join (select null as pilot1_non_unique_initials from dual) d1
      on 1 = 1
    join (SELECT null as pilot2_non_unique_initials from dual) d2 -- kenneth made beautiful sql --
      on 1 = 1
);

--set to true if the same initials are found more than once
update flagged_for_duplicated_initials set
  pilot1_non_unique_initials = 'T' where pilot1init in (SELECT initials from d_member group by initials having count(*) > 1)
;

update flagged_for_duplicated_initials set
  pilot2_non_unique_initials = 'T' where pilot2init in (SELECT initials from d_member group by initials having count(*) > 1)
;

-- now flights are valid
insert into flights_to_load (LAUNCHTIME, LANDINGTIME,PLANEREGISTRATION,PILOT1INIT,PILOT2INIT,LAUNCHAEROTOW,LAUNCHWINCH,LAUNCHSELFLAUNCH,
                             CABLEBREAK ,CROSSCOUNTRYKM,CLUBNAME,PILOT1_NON_UNIQUE_INITIALS,PILOT2_NON_UNIQUE_INITIALS, DURATION)
  (
    SELECT
      LAUNCHTIME, LANDINGTIME,PLANEREGISTRATION,PILOT1INIT,
      PILOT2INIT,LAUNCHAEROTOW,LAUNCHWINCH,LAUNCHSELFLAUNCH,
      CABLEBREAK ,CROSSCOUNTRYKM,CLUBNAME,PILOT1_NON_UNIQUE_INITIALS,
      PILOT2_NON_UNIQUE_INITIALS,
      launchtime - landingtime as duration
    FROM flagged_for_duplicated_initials
  );

COMMIT ;