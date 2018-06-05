-------- INIT FLIGHTS Extract --------


BEGIN drop_table('lastDateOfFlightExtraction'); END;
/
BEGIN drop_table('toHandleLaterFlights'); END;
/

CREATE TABLE toHandleLaterFlights AS (SELECT * FROM TAFLIGHTSVEJLE);
ALTER TABLE toHandleLaterFlights ADD (clubName varchar2(100));
CREATE TABLE lastDateOfFlightExtraction (lastDate date);
INSERT INTO lastDateOfFlightExtraction (lastDate) VALUES (to_date('0001-01-01', 'YYYY-MM-DD'));

------------INIT MEMBER Extract-----------

BEGIN drop_table('deltaMember'); END;
/
BEGIN drop_table('MEMBER_YESTERDAY'); END;
/

-- Creation of table with the members that have changed
Create TABLE deltaMember as (SELECT * FROM taMember where 1 = 0);
-- Adding operation code that identifies what type of change happened
ALTER TABLE deltaMember ADD (operation char(3));
-- Table to keep track of extraction of last day
Create TABLE MEMBER_YESTERDAY as (SELECT * FROM taMember where 1 = 0);

COMMIT;
