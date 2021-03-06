-------- LOAD FLIGHTS------
-------- TRUNCATE ------
truncate table MEMBER_TO_LOAD;

insert into member_to_load (select * from fixed_status_member);

-- inserting new rows into dimension d_member ---
-- If the operation code is 'INS', that means the row was inserted since last extraction,
-- so valid_from is set to today and the valid_to is set to the end of time.
insert INTO d_member(MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED, DATELEFT, OWNSPLANEREG, SEX, CLUB, STATUS,  MEMBER_ID, valid_from, valid_to)
  ( SELECT
      MEMBERNO, INITIALS, NAME,ADDRESS,
      ZIPCODE, DATEBORN, DATEJOINED, DATELEFT,
      OWNSPLANEREG, SEX, CLUB, STATUS,
      d_member_id.nextval as MEMBER_ID,
      trunc(sysdate, 'DAY') as valid_from,
      to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') as valid_to
    from MEMBER_TO_LOAD
    where operation = 'INS'
  );
--finished handling inserted rows

-- handling deleted rows
-- if the operation code is DEL that means the row was deleted
-- since the last day of extraction,
-- so valid_from is still the same and valid_to is set to yesterday
update d_member set
  valid_to = (trunc(sysdate-1,'DAY'))
where valid_to = to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') AND
      MEMBERNO in (select memberno from MEMBER_TO_LOAD where operation = 'DEL')
;
-- finished handling deleted rows

--handling changed rows
-- if the operation code is CHG that means the row was changed
-- since the last day of extraction,
-- so valid_from is still the same and valid_to is set to yesterday
update d_member set
  valid_to = (trunc(sysdate-1,'DAY'))
where valid_to = to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') AND
      MEMBERNO in (select memberno from MEMBER_TO_LOAD where operation = 'CHG')
;

--- loads into the dimension member ---
insert INTO d_member(MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED, DATELEFT, OWNSPLANEREG, SEX, CLUB, STATUS,  MEMBER_ID,valid_from,valid_to)
  (SELECT
     MEMBERNO, INITIALS, NAME,ADDRESS, ZIPCODE,
     DATEBORN, DATEJOINED, DATELEFT,
     OWNSPLANEREG, SEX, CLUB, STATUS,
     d_member_id.nextval as MEMBER_ID,
     trunc(sysdate, 'DAY') as valid_from,
     to_date('9999-12-31 00:00:00', 'YYYY-MM-DD HH24:MI:SS') as valid_to
   from MEMBER_TO_LOAD
   where operation = 'CHG'
  )
;
-- finish handling changed rows
COMMIT ;