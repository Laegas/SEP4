BEGIN
  EXECUTE IMMEDIATE 'drop sequence igc_weather_surr_key';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -2289 THEN
    RAISE;
  END IF;
END;

----sequence for D_IGC_WEATHER surr_key----
create SEQUENCE igc_weather_surr_key
  START WITH 1
  INCREMENT BY 1
  CACHE 100
  NOMAXVALUE
;