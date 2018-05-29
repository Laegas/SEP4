-------- finding new flights------
BEGIN
  EXECUTE IMMEDIATE 'DROP TABLE toHandleLaterFlights';
  EXECUTE IMMEDIATE 'DROP TABLE lastExtractedFlights';

  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/
CREATE TABLE toHandleLaterFlights AS (SELECT * FROM TAFLIGHTSVEJLE where 1=0);
ALTER TABLE toHandleLaterFlights ADD (clubName VARCHAR(100));

CREATE TABLE lastExtractedFlights (lastDate date);
INSERT INTO LASTEXTRACTEDFLIGHTS (lastDate) VALUES (CURRENT_DATE);

UPDATE lastExtractedFlights set lastDate = CURRENT_DATE;

-- inserts all flights that launched later than the last extract date
INSERT INTO TOHANDLELATERFLIGHTS
  (
    SELECT * FROM TAFLIGHTSVEJLE JOIN
      (SELECT 'vejle' as clubname FROM dual)
        ON 1=1
    WHERE launchtime > (select lastdate FROM LASTEXTRACTEDFLIGHTS)
  );
INSERT INTO TOHANDLELATERFLIGHTS
  (
    SELECT * FROM TAFLIGHTSSG70 JOIN
      (SELECT 'SG70' as clubname FROM dual)
        ON 1=1
    WHERE launchtime > (select lastdate FROM LASTEXTRACTEDFLIGHTS)
  );
---------- finished finding flights ----------

COMMIT;