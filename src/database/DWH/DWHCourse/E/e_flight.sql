-------- EXTRACT FLIGHTS------
-------- TRUNCATE ------
TRUNCATE TABLE toHandleLaterFlights;

-- inserts all flights that launched later than the last extract date
-- flights from vejle
INSERT INTO TOHANDLELATERFLIGHTS
  (
    SELECT * FROM TAFLIGHTSVEJLE JOIN
      (SELECT 'vejle' as clubname FROM dual)
        ON 1=1
    WHERE launchtime > (select lastdate FROM lastDateOfFlightExtraction)
  );

-- flights from SG70
INSERT INTO TOHANDLELATERFLIGHTS
  (
    SELECT * FROM TAFLIGHTSSG70 JOIN
      (SELECT 'SG70' as clubname FROM dual)
        ON 1=1
    WHERE launchtime > (select lastdate FROM lastDateOfFlightExtraction)
  );
---------- finished finding flights ----------


UPDATE lastDateOfFlightExtraction set lastDate = CURRENT_DATE;

