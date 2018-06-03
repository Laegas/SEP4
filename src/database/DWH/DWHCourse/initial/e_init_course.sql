-------- INIT FLIGHTS Extract --------

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE lastDateOfFlightExtraction cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE toHandleLaterFlights cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

CREATE TABLE toHandleLaterFlights AS (SELECT * FROM TAFLIGHTSVEJLE);
ALTER TABLE toHandleLaterFlights ADD (clubName varchar2(100));
CREATE TABLE lastDateOfFlightExtraction (lastDate date);
INSERT INTO lastDateOfFlightExtraction (lastDate) VALUES (to_date('0001-01-01', 'YYYY-MM-DD'));

------------INIT MEMBER Extract-----------

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE MEMBER_YESTERDAY cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE deltamember cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
Create TABLE deltaMember as (SELECT * FROM taMember where 1 = 0); -- Creation of table with the members that have changed
ALTER TABLE deltaMember ADD (operation char(3)); -- Adding operation code that identifies what type of change happened.
Create TABLE MEMBER_YESTERDAY as (SELECT * FROM taMember where 1 = 0); -- Table to keep track of extraction of last day.

COMMIT;
