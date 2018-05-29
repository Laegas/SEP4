delete from IGC_SOURCE_DATA where id in (select igc_id from FULLY_EXTRACTED_IGC);
DELETE from data_logger_source where id in (select flight_id FROM FULLY_EXTRACTED_IGC);
