--------------- EXTRACTING MEMBERS -------------------
--------------- TRUNCATE ---------------------
truncate table deltaMember;
--inserting all the inserted rows into the delta table
INSERT INTO deltaMember (
  MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
  DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT,
  STATUSASCAT, STATUSFULLCAT, SEX, CLUB,OPERATION
) (SELECT MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
     DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT, STATUSASCAT, STATUSFULLCAT, SEX, CLUB, 'INS'
   FROM taMember where MemberNo in
       (SELECT memberno from taMember
        minus
        SELECT memberno from member_yesterday)
  );

--inserting all the deleted rows
INSERT INTO deltaMember(OPERATION, MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
                        DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT,
                        STATUSASCAT, STATUSFULLCAT, SEX, CLUB)
  (SELECT 'DEL', MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
    DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT, STATUSASCAT, STATUSFULLCAT, SEX, CLUB
  FROM member_yesterday where MemberNo in(
    SELECT memberno FROM member_yesterday
    minus
    SELECT memberno from taMember
  ));

-- inserting all the changed rows TODO
INSERT INTO deltaMember( MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
                         DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT,
                         STATUSASCAT, STATUSFULLCAT, SEX, CLUB,OPERATION)
  ( SELECT MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
    DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT,
    STATUSASCAT, STATUSFULLCAT, SEX, CLUB,OPERATION FROM
    ((SELECT MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
        DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT, STATUSASCAT, STATUSFULLCAT, SEX, CLUB,'CHG' as Operation
      FROM taMember)
     minus
     (SELECT  MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
        DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT, STATUSASCAT, STATUSFULLCAT, SEX, CLUB,'CHG' as Operation
      FROM member_yesterday))
  where memberno not in (SELECT memberno from deltaMember where operation = 'INS'));

-- extracting all the people with birthdays
INSERT into deltamember( MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
                         DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT,
                         STATUSASCAT, STATUSFULLCAT, SEX, CLUB,OPERATION)
  (SELECT  MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
    DATELEFT, OWNSPLANEREG, STATUSSTUDENT, STATUSPILOT,
    STATUSASCAT, STATUSFULLCAT, SEX, CLUB, 'CHG' as Operation FROM taMember
  where
    extract(day from dateborn) = extract(day from sysdate) AND
    extract(month from dateborn) = extract(month from sysdate) AND
    memberno not in(
      SELECT memberno from deltamember where operation = 'INS' OR operation = 'CHG'
    ));

-- updating the member_yesterday table to hold the new data
truncate table member_yesterday;
insert into member_yesterday (select * from taMember);