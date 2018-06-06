
--- procedure for dropping sequence
CREATE OR REPLACE PROCEDURE drop_sequence(sequence_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP SEQUENCE ' || sequence_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -2289 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping table
CREATE OR REPLACE PROCEDURE drop_table(table_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE ' || table_name || ' cascade constraints purge';
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -942 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping view
CREATE OR REPLACE PROCEDURE drop_view(view_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP VIEW ' || view_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -942 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping trigger
CREATE OR REPLACE PROCEDURE drop_trigger(trigger_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP TRIGGER ' || trigger_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -4080 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping index
CREATE OR REPLACE PROCEDURE drop_index(index_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP INDEX ' || index_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -1418 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping column
CREATE OR REPLACE PROCEDURE drop_column(table_name IN VARCHAR2, column_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE ' || table_name
                      || ' DROP COLUMN ' || column_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -904 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping database link
CREATE OR REPLACE PROCEDURE drop_database_link(dblink_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP DATABASE LINK ' || dblink_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -2024 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping materialized view
CREATE OR REPLACE PROCEDURE drop_materialized_view(mview_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP MATERIALIZED VIEW ' || mview_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -12003 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping type
CREATE OR REPLACE PROCEDURE drop_type(type_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP TYPE ' || type_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping constraint
CREATE OR REPLACE PROCEDURE drop_constraint(table_name IN VARCHAR2, constraint_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'ALTER TABLE ' || table_name
                      || ' DROP CONSTRAINT ' || constraint_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -2443 THEN
      RAISE;
    END IF;
  END;
/

-- CREATE OR REPLACE PROCEDURE drop_scheduler_job(job_name IN VARCHAR2)
-- IS
-- BEGIN
--   DBMS_SCHEDULER.drop_job(job_name);
--   EXCEPTION
--   WHEN OTHERS THEN
--   IF SQLCODE != -27475 THEN
--     RAISE;
--   END IF;
-- END;
-- /

--- procedure for dropping user / schema
CREATE OR REPLACE PROCEDURE drop_user(user_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP USER ' || user_name;
    /* you may or may not want to add CASCADE */
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -1918 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping package
CREATE OR REPLACE PROCEDURE drop_package(package_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP PACKAGE ' || package_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping procedure
CREATE OR REPLACE PROCEDURE drop_procedure(procedure_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP PROCEDURE ' || procedure_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping function
CREATE OR REPLACE PROCEDURE drop_type(function_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP FUNCTION ' || function_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
  END;
/

--- procedure for dropping tablespace
CREATE OR REPLACE PROCEDURE drop_type(tablespace_name IN VARCHAR2)
  IS
  BEGIN
    EXECUTE IMMEDIATE 'DROP TABLESPACE' || tablespace_name;
    EXCEPTION
    WHEN OTHERS THEN
    IF SQLCODE != -959 THEN
      RAISE;
    END IF;
  END;
/
