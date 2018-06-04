-------- INIT FLIGHTS Transform ------
BEGIN drop_table('fixed_pilot_info'); END;/
BEGIN drop_table('flagged_for_duplicated_initials'); END;/
BEGIN drop_table('fixed_Land_Launch_Time'); END;/

create table fixed_Land_Launch_Time as (select * from toHandleLaterFlights where 1 = 0);
create table fixed_pilot_info as (select * from toHandleLaterFlights where 1 = 0);
create table flagged_for_duplicated_initials as (select * from toHandleLaterFlights where 1 = 0);
alter table flagged_for_duplicated_initials add (pilot1_NON_unique_initials char(1), pilot2_non_unique_initials char(1));

------------INIT MEMBER Transform-----------
BEGIN drop_table('fixed_status_member'); END;/

CREATE TABLE fixed_status_member As (SELECT * FROM deltaMember where 1 = 0); -- Status is changed in this table. 4 columns to 1.
ALTER TABLE fixed_status_member drop (statusstudent, statuspilot, statusFullCat, statusAsCat); -- replacing 4
-- existing status columns
alter table fixed_status_member add (status varchar2(255)); -- by one that contains full name of the status