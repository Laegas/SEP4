-------- INIT FLIGHTS Extract --------

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE toHandleLaterFlights';
   EXECUTE IMMEDIATE 'DROP TABLE lastDateOfFlightExtraction';
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
   EXECUTE IMMEDIATE 'DROP TABLE deltamember';
   EXECUTE IMMEDIATE 'DROP TABLE MEMBER_YESTERDAY';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
CREate TABLE deltaMember as (SELECT * FROM taMember where 1 = 0);
ALTER TABLE deltaMember ADD (operation char(3));
Create TABLE MEMBER_YESTERDAY as (SELECT * FROM taMember where 1 = 0);

COMMIT;
