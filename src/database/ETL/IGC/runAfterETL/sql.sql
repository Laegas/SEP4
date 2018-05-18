delete from IGC_SOURCE_DATA where id in (select id from FULLY_EXTRACTED_IGC);
DELETE from data_logger where id in (select flightid FROM FULLY_EXTRACTED_IGC);
