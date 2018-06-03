-------- INIT FLIGHTS Transform ------
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE fixed_pilot_info cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE flagged_for_duplicated_initials cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE fixed_Land_Launch_Time cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/

create table fixed_Land_Launch_Time as (select * from toHandleLaterFlights where 1 = 0);
create table fixed_pilot_info as (select * from toHandleLaterFlights where 1 = 0);
create table flagged_for_duplicated_initials as (select * from toHandleLaterFlights where 1 = 0);
alter table flagged_for_duplicated_initials add (pilot1_NON_unique_initials char(1), pilot2_non_unique_initials char(1));

------------INIT MEMBER Transform-----------

BEGIN
   EXECUTE IMMEDIATE 'DROP TABLE fixed_status_member cascade constraints purge';
EXCEPTION
   WHEN OTHERS THEN
      IF SQLCODE != -942 THEN
         RAISE;
      END IF;
END;
/
CREATE TABLE fixed_status_member As (SELECT * FROM deltaMember where 1 = 0); -- Status is changed in this table. 4 columns to 1.
ALTER TABLE fixed_status_member drop (statusstudent, statuspilot, statusFullCat, statusAsCat);
alter table fixed_status_member add (status varchar2(255));