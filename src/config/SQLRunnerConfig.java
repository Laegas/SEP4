package config;


import java.io.File;

/**
 * Created by kenneth on 23/05/2018.
 */
public class SQLRunnerConfig {
    // ETL DWH Course
    public static final File COURSE_E_INIT = new File("src/database/ETL/DWHCourse/initial/e_init_course.sql");
    public static final File COURSE_T_INIT = new File("src/database/ETL/DWHCourse/initial/t_init_course.sql");
    public static final File COURSE_L_INIT = new File("src/database/ETL/DWHCourse/initial/l_init_course.sql");

    public static final File COURSE_E_MEMBER = new File("src/database/ETL/DWHCourse/E/e_member.sql");
    public static final File COURSE_E_FLIGHT = new File("src/database/ETL/DWHCourse/E/e_flight.sql");
    public static final File COURSE_T_MEMBER = new File("src/database/ETL/DWHCourse/T/t_member.sql");
    public static final File COURSE_T_FLIGHT = new File("src/database/ETL/DWHCourse/T/t_flight.sql");
    public static final File COURSE_L_MEMBER = new File("src/database/ETL/DWHCourse/L/l_member.sql");
    public static final File COURSE_L_FLIGHT = new File("src/database/ETL/DWHCourse/L/l_flight.sql");

    // ETL IGC
    public static final File IGC_E_INIT = new File("src/database/ETL/IGC/initial/e_init_igc.sql");
    public static final File IGC_T_INIT = new File("src/database/ETL/IGC/initial/t_init_igc.sql");
    public static final File IGC_L_INIT = new File("src/database/ETL/IGC/initial/l_init_igc.sql");

    public static final File IGC_E = new File("src/database/ETL/IGC/E/e_igc.sql");
    public static final File IGC_T = new File("src/database/ETL/IGC/T/t_igc.sql");
    public static final File IGC_L = new File("src/database/ETL/IGC/L/l_igc.sql");

    public static final File IGC_AFTER_ETL = new File("src/database/ETL/IGC/runAfterETL/sql.sql");

    // ETL Weather
    public static final File WEATHER_E_INIT = new File("src/database/ETL/weather/initial/e_init_weather.sql");
    public static final File WEATHER_T_INIT = new File("src/database/ETL/weather/initial/t_init_weather.sql");
    public static final File WEATHER_L_INIT = new File("src/database/ETL/weather/initial/l_init_weather.sql");

    public static final File WEATHER_E = new File("src/database/ETL/weather/E/e_weather.sql");
    public static final File WEATHER_T = new File("src/database/ETL/weather/T/t_weather.sql");
    public static final File WEATHER_L = new File("src/database/ETL/weather/L/l_weather.sql");

    // DDL
    public static final File IGC_SOURCE_TABLE_DDL = new File("src/database/DDL/igc_source_table_ddl.sql");
    public static final File WEATHER_SOURCE_TABLE_DDL = new File("src/database/DDL/weather_source_table_ddl.sql");

    public static final File DIMENSIONAL_MODEL_DDL = new File("src/database/DDL/dimensional_igc_model_ddl.sql");
    public static final File DIMENSIONAL_WEATHER_DDL = new File("src/database/DDL/dimensional_model_weather_ddl.sql");
    public static final File DIMENSIONAL_COURSE_DDL = new File("src/database/DDL/dimensional_model_course_DWH_ddl.sql");
}
