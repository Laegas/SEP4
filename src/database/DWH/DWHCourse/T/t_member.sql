-------------- TRANSFORM MEMBERS -------------------
-------------- TRUNCATE ------------
truncate table fixed_status_member;

--cleaning up status
INSERT INTO fixed_status_member (OPERATION, MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE,
                                 DATEBORN, DATEJOINED, DATELEFT, OWNSPLANEREG, status, SEX, CLUB)
  SELECT OPERATION , MEMBERNO, INITIALS, NAME, ADDRESS, ZIPCODE, DATEBORN, DATEJOINED,
    DATELEFT,
    OWNSPLANEREG,
    case
    when substr(STATUSSTUDENT || STATUSPILOT || STATUSASCAT || STATUSFULLCAT,4,1) = 'Y'
      then 'Full Category Instructor'
    when substr(STATUSSTUDENT || STATUSPILOT || STATUSASCAT || STATUSFULLCAT,3,1) = 'Y'
      then 'Assistant Category Instructor'
    when substr(STATUSSTUDENT || STATUSPILOT || STATUSASCAT || STATUSFULLCAT,2,1) = 'Y'
      then 'Pilot'
    else
      'Student'
    end "status",
    SEX, CLUB
  FROM deltamember;

--fix date left in the fixed state table
update fixed_status_member
set dateleft = TO_DATE('9999-12-31', 'yyyy-mm-dd')
where dateleft IS null;