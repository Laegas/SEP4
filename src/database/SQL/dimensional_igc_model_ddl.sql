

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
  glider_id varchar2(10),
  id_valid_from int references D_DATE(ID_DATE),
  id_valid_to int references D_DATE(ID_DATE)
);

create table D_FLIGHT(
  surr_key_flight int PRIMARY KEY,
  surr_key_glider int references D_GLIDER(surr_key_glider),
  id_start_date int references D_DATE(ID_DATE)
);

CREATE table F_IGC_LOG(
  SURR_KEY_LOG int,
  surr_key_flight int REFERENCES D_FLIGHT(surr_key_flight),
  id_time int references D_TIME(ID_TIME),
  lat_north char(7),
  long_east char(8),
  press_altitude INT,
  gps_altitude INT,
  gps_ok char(1),
  PRIMARY KEY (surr_key_flight, id_time)
);

COMMIT;

select * from d_flight;
