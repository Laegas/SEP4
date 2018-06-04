
truncate table flights_to_load_with_surr_key;
--inserting everything from flights_to_load into flights_to_load_with_surr_key
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

--setting id_launchtime to according id_time from dimension D_Time
update flights_to_load_with_surr_key a set id_launchtime = (
  select id_time from d_time where
    d_time.hour = to_char(a.LAUNCHTIME, 'HH') AND
    d_time.minute = to_char(a.LAUNCHTIME, 'MI') AND
    d_time.second = to_char(a.LAUNCHTIME, 'SS')
);

--setting id_landtime to according id_time from dimension D_Time
update flights_to_load_with_surr_key a set id_LANDTIME = (
  select id_time from d_time where
    d_time.hour = to_char(a.LANDINGTIME, 'HH') AND
    d_time.minute = to_char(a.LANDINGTIME, 'MI') AND
    d_time.second = to_char(a.LANDINGTIME, 'SS')
);

--setting id_launchdate to according id_date from dimension D_Date
update flights_to_load_with_surr_key a set a.id_LAUNCHDATE = (
  select id_date from d_date where
    d_date.year = to_char(extract(year from a.LAUNCHTIME)) AND
    d_date.month= to_char(extract(month from a.LAUNCHTIME)) AND
    d_date.day = to_char(extract(day from a.LAUNCHTIME))
);

--setting id_landdate to according id_date from dimension D_Date
update flights_to_load_with_surr_key a set a.id_LANDDATE = (
  select id_date from d_date where
    d_date.year = to_char(extract(year from a.LANDINGTIME)) AND
    d_date.month= to_char(extract(month from a.LANDINGTIME)) AND
    d_date.day = to_char(extract(day from a.LANDINGTIME))
);

-- start load of bridge table and flights
declare
  tmp_id_member int;
begin
  --loop for each flight
  for c in (SELECT
              pilot1init, pilot2init, pilot1_Non_UNIQUE_INITIALS, pilot2_Non_UNIQUE_INITIALS,
              id_launchtime , id_landtime , id_landdate, ID_LAUNCHDATE
            from flights_to_load_with_surr_key) loop
    --if the pilot 1 initials are not unique - set the member_id to -1
    --otherwise, select the member_id from dimension D_member where the initials are a match
    if(c.pilot1_non_unique_initials = 'T')
    then
      tmp_id_member := -1;
    else
      select (SELECT member_id from d_member where INITIALS = c.pilot1init offset 0 rows fetch next 1 rows only) into tmp_id_member from dual;
    end if;
    dbms_output.put_line(tmp_id_member);
    --if the pilot 2 initials are empty,
    --then insert an id_group (from sequence), id_member (tmp_id_member) and a weight of 1.0
    --as empty pilot2init means that there is only one pilot for the flight
    if(c.pilot2init = '    ')
    then
      dbms_output.put_line(tmp_id_member);
      insert into B_FLIGHT_MEMBER (ID_GROUP, ID_MEMBER, weight) VALUES (
        SEQ_ID_B_FLIGHT_MEMBER.nextVAl, tmp_id_member  , 1.0);
    else
      -- if pilot2init is not empty
      -- first insert pilot 1
      insert into B_FLIGHT_MEMBER (ID_GROUP, ID_MEMBER, weight) VALUES (
        SEQ_ID_B_FLIGHT_MEMBER.nextVAl, tmp_id_member  , 0.5);
      -- taking care of pilot 2
      -- do check for non-unique initials as for pilot1init
      if(c.pilot2_non_unique_initials = 'T')
      then
        tmp_id_member := -1;
      else
        select (SELECT member_id from d_member where INITIALS = c.pilot2init offset 0 rows fetch next 1 rows only) into tmp_id_member from dual;
        if(tmp_id_member is null)
        then
          tmp_id_member := -1;
        end if;
      end if;
      --once done, can insert into bridge table
      -- B_FLIGHT_MEMBER the id_group, id_member and a weight of 0.5
      -- as there are two pilots for the flight
      insert into B_FLIGHT_MEMBER (ID_GROUP, ID_MEMBER, weight) VALUES (
        SEQ_ID_B_FLIGHT_MEMBER.currval, tmp_id_member  , 0.5);
    end if;
    --inserting flight facts
    insert into F_FLIGHT(  id_group, id_launch_time , id_land_time , id_land_date , duration, ID_LAUNCH_DATE) VALUES (
      SEQ_ID_B_FLIGHT_MEMBER.currval,
      c.id_launchtime,
      c.id_landtime,
      c.id_landdate,
      120,
      c.ID_LAUNCHDATE
    );
  end loop;
end;
/





/*used for the testing of f_flight*/
select b.ID_GROUP, b.WEIGHT, ID_MEMBER, m.INITIALS, m.NAME, d.YEAR as "launch year", d.MONTH as "launch month", d.DAY as "launch day"
  from B_FLIGHT_MEMBER b
    join D_MEMBER m
      on b.ID_MEMBER = m.MEMBER_ID
    join F_FLIGHT f
      on f.ID_GROUP = b.ID_GROUP
    join D_DATE d
      on f.ID_LAUNCH_TIME = d.ID_DATE
  where b.id_group = 1511;

select b.ID_GROUP, b.WEIGHT, ID_MEMBER, m.INITIALS, m.NAME, d.YEAR as "launch year", d.MONTH as "launch month", d.DAY as "launch day"
  from B_FLIGHT_MEMBER b
    join D_MEMBER m
      on b.ID_MEMBER = m.MEMBER_ID
    join F_FLIGHT f
      on f.ID_GROUP = b.ID_GROUP
    join D_DATE d
      on f.ID_LAUNCH_TIME = d.ID_DATE
  where b.id_group = 1478;


