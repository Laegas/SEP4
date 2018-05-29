

BEGIN
  EXECUTE IMMEDIATE 'drop table D_GLIDER CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table D_FLIGHT CASCADE CONSTRAINTS purge';
  EXECUTE IMMEDIATE 'drop table F_IGC_LOG CASCADE CONSTRAINTS purge';
  EXCEPTION
  WHEN OTHERS THEN
  IF SQLCODE != -942 THEN
    RAISE;
  END IF;
END;
/




create table D_GLIDER (
  surr_key_glider int PRIMARY KEY,
  glider_id varchar22(10)
);

create table D_FLIGHT(
  surr_key_flight int PRIMARY KEY,
  start_date date
);


CREATE table F_IGC_LOG(
  surr_key_log INT,
  surr_key_flight INT REFERENCES D_FLIGHT(surr_key_flight),
  surr_key_glider INT REFERENCES D_GLIDER(surr_key_glider),
  time TIMESTAMP,
  lat_north char(7),
  long_east char(8),
  press_altitude INT,
  gps_altitude INT,
  gps_ok char(1),
  PRIMARY KEY (surr_key_log,surr_key_flight,surr_key_glider)
);

COMMIT;

select * from d_flight;
