
-- now loading the flights
--------------------- INIT --------------


----------- FINISHED INIT ------------
truncate table flights_to_load_with_surr_key;
insert into flights_to_load_with_surr_key (LAUNCHTIME,LANDINGTIME,PLANEREGISTRATION,
                                           PILOT1INIT,PILOT2INIT,CROSSCOUNTRYKM,CLUBNAME,
                                           PILOT1_NON_UNIQUE_INITIALS,PILOT2_NON_UNIQUE_INITIALS,
                                           DURATION )
                                          (select LAUNCHTIME,LANDINGTIME,PLANEREGISTRATION,
                                           PILOT1INIT,PILOT2INIT,CROSSCOUNTRYKM,CLUBNAME,
                                           PILOT1_NON_UNIQUE_INITIALS,PILOT2_NON_UNIQUE_INITIALS,
                                           DURATION
                                           from flights_to_load
  );



-- id_member = 0 is for members with non unique initials
--describe flights_to_load_with_surr_key;
/*
update flights_to_load_with_surr_key a set
  id_member = (select id_member, valid_TO from d_member where initials = a.init AND
                  valid_to = trunc(TO_DATE('9999-12-31' ,'YYYY-MM-DD'),'DAY'))
    where non_unique_initials <> 'T'
;
*/
update flights_to_load_with_surr_key set id_flight = seq_id_flights.NEXTVAL
;


update flights_to_load_with_surr_key a set id_launchtime = (
  select id_time from d_time where
    d_time.hour = to_char(a.LAUNCHTIME, 'HH') AND
    d_time.minute = to_char(a.LAUNCHTIME, 'MI') AND
    d_time.second = to_char(a.LAUNCHTIME, 'SS')
);

update flights_to_load_with_surr_key a set id_LANDTIME = (
  select id_time from d_time where
    d_time.hour = to_char(a.LANDINGTIME, 'HH') AND
    d_time.minute = to_char(a.LANDINGTIME, 'MI') AND
    d_time.second = to_char(a.LANDINGTIME, 'SS')
);

update flights_to_load_with_surr_key a set a.id_LAUNCHDATE = (
  select id_date from d_date where
    d_date.year = extract(year from a.LAUNCHTIME) AND
    d_date.month= extract(month from a.LAUNCHTIME) AND
    d_date.day = extract(day from a.LAUNCHTIME)
);

update flights_to_load_with_surr_key a set a.id_LANDDATE = (
  select id_date from d_date where
    d_date.year = extract(year from a.LANDINGTIME) AND
    d_date.month= extract(month from a.LANDINGTIME) AND
    d_date.day = extract(day from a.LANDINGTIME)
);


--describe flights_to_load_with_surr_key;
--describe d_member;
-- now starts the fun stuff with bridge tables and members

--describe d_member;
--describe flights_to_load_with_surr_key;

declare
  tmp_id_member int;
begin
  for c in (SELECT
              pilot1init, pilot2init, pilot1_Non_UNIQUE_INITIALS, pilot2_Non_UNIQUE_INITIALS,
              id_launchtime , id_landtime , id_landdate, surr_key_flight
            from flights_to_load_with_surr_key) loop
    if(c.pilot1_non_unique_initials <> 'T')
    then
      tmp_id_member := 0;
    else
      select (SELECT member_id from d_member where INITIALS = c.pilot1init offset 0 rows fetch next 1 rows only) into tmp_id_member from dual;
    end if;
    if(c.pilot2init = '    ')
    then
      insert into B_FLIGHT_MEMBER (ID_GROUP, ID_MEMBER, weight) VALUES (
        SEQ_ID_B_FLIGHT_MEMBER.nextVAl, tmp_id_member  , 1.0);
    else
      -- first insert pilot 1
      insert into B_FLIGHT_MEMBER (ID_GROUP, ID_MEMBER, weight) VALUES (
        SEQ_ID_B_FLIGHT_MEMBER.nextVAl, tmp_id_member  , 0.5);
      --taking care of pilot 2
      if(c.pilot2_non_unique_initials <> 'T')
      then
        tmp_id_member := 0;
      else
        select (SELECT member_id from d_member where INITIALS = c.pilot2init offset 0 rows fetch next 1 rows only) into tmp_id_member from dual;
        if(tmp_id_member = null)
        then
          tmp_id_member := 0;
        end if;
      end if;
      insert into B_FLIGHT_MEMBER (ID_GROUP, ID_MEMBER, weight) VALUES (
        SEQ_ID_B_FLIGHT_MEMBER.currval, tmp_id_member  , 0.5);
    end if;
    --inserting flight facts
    insert into f_flight(  id_group, ID_MEMBER, SURR_KEY_FLIGHT, id_launch_time , id_land_time , id_land_date , duration) VALUES (
      SEQ_ID_B_FLIGHT_MEMBER.currval,
      tmp_id_member,
      c.surr_key_flight,
      c.id_launchtime,
      c.id_landtime,
      c.id_landdate,
      120
    );
  end loop;
end;
/


-- insert into flights
