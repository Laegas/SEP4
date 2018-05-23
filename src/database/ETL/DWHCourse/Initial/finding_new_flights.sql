-------- finding new flights------

-- CREATE TABLE toHandleLaterFlights AS (SELECT * FROM TAFLIGHTSVEJLE);
-- ALTER TABLE toHandleLaterFlights ADD (clubName VARCHAR(100));

-- CREATE TABLE lastExtractedFlights (lastDate date);
-- INSERT INTO LASTEXTRACTEDFLIGHTS (lastDate) VALUES (CURRENT_DATE);

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