
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
