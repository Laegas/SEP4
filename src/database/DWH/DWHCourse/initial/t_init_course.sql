-------- INIT FLIGHTS Transform ------
--drop tables if they exist
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
--create tables to hold data after each step of transformation

--land and launch time is fixed in this table -
--swapped if land - launch time is <0;
--launch and land time set to the same date if land time is 31-12-9999
create table fixed_Land_Launch_Time as (select * from toHandleLaterFlights where 1 = 0);
create table fixed_pilot_info as (select * from toHandleLaterFlights where 1 = 0);

--table used to flag member records for duplicate initials
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
-- Status is changed in this table.
-- 4 columns from the source tables to 1 column in D_Member.
CREATE TABLE fixed_status_member As (SELECT * FROM deltaMember where 1 = 0);
ALTER TABLE fixed_status_member drop (statusstudent, statuspilot, statusFullCat, statusAsCat);
alter table fixed_status_member add (status varchar2(255));