CREATE INDEX INDEX_TO_D_TIME_HOUR on  TRANSFORM_IGC_EMPTY_GLIDER_REGNO(to_char(TIME_OF_LOG, 'HH24'));
CREATE INDEX INDEX_TO_D_TIME_MINUTE on TRANSFORM_IGC_EMPTY_GLIDER_REGNO(to_char(TIME_OF_LOG, 'MI'));
CREATE INDEX INDEX_TO_D_TIME_SECOND on TRANSFORM_IGC_EMPTY_GLIDER_REGNO(to_char(TIME_OF_LOG, 'SS'));

BEGIN
  EXECUTE IMMEDIATE 'drop sequence glider_surr_key';
EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;

create SEQUENCE glider_surr_key
  START WITH 1
  INCREMENT BY 1
  CACHE 100
  NOMAXVALUE
;

COMMIT;
