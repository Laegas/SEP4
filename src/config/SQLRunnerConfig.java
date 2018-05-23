package config;


import java.io.File;

/**
 * Created by kenneth on 23/05/2018.
 */
public class SQLRunnerConfig {
    public static final File IGC_E_INIT = new File("src/database/ETL/IGC/initial/e_init_igc.sql");
    public static final File IGC_T_INIT = new File("src/database/ETL/IGC/initial/t_init_igc.sql");
    public static final File IGC_L_INIT = new File("src/database/ETL/IGC/initial/l_init_igc.sql");
    public static final File IGC_SOURCE_TABLE_DDL = new File("src/database/SQL/igc_source_table_ddl.sql");


    public static final File IGC_E = new File("src/database/ETL/IGC/E/e_igc.sql");
    public static final File IGC_T = new File("src/database/ETL/IGC/T/t_igc.sql");
    public static final File IGC_L = new File("src/database/ETL/IGC/L/l_igc.sql");

    public static final File IGC_AFTER_ETL = new File("src/database/ETL/IGC/runAfterETL/sql.sql");

    public static final File DIMENSIONAL_MODEL_DDL = new File("src/database/SQL/dimensional_igc_model_ddl.sql");
    public static final File DIMENSIONAL_WEATHER_DDL = new File("src/database/SQL/weather_source_table_ddl.sql");

    public static final File WEATHER_SOURCE_TABLE_DDL = new File("src/database/SQL/weather_source_table_ddl.sql");
    public static final File WEATHER_E_INIT = new File("src/database/ETL/weather/initial/e_init_weather.sql");
    public static final File WEATHER_T_INIT = new File("src/database/ETL/weather/initial/t_init_weather.sql");

    public static final File WEATHER_E = new File("src/database/ETL/weather/E/e_weather.sql");
    public static final File WEATHER_T = new File("src/database/ETL/weather/T/t_weather.sql");
    public static final File WEATHER_L = new File("src/database/ETL/weather/L/l_weather.sql");



//    public static void main(String[] args) {
//        File IGC_INIT = new File("database/ETL/IGC/initial/e_init_igc.sql");
//        System.out.println(IGC_INIT.getAbsoluteFile());
//    }


}
