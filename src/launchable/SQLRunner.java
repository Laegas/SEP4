package launchable;

import config.DatabaseConfig;
import config.SQLRunnerConfig;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class SQLRunner {

    public static void main(String[] args) {
        runAllDDL();
//        runETL();
//        runAfterETL();
    }

    public static void runAllDDL() {
        // DDL - Dimensional
        executeSql(SQLRunnerConfig.DIMENSIONAL_MODEL_DDL.getAbsolutePath());
        executeSql(SQLRunnerConfig.DIMENSIONAL_WEATHER_DDL.getAbsolutePath());
        executeSql(SQLRunnerConfig.DIMENSIONAL_COURSE_DDL.getAbsolutePath());

        // DDL - source tables
        executeSql(SQLRunnerConfig.WEATHER_SOURCE_TABLE_DDL.getAbsolutePath());
        executeSql(SQLRunnerConfig.IGC_SOURCE_TABLE_DDL.getAbsolutePath());

        // ETL - init
        executeSql(SQLRunnerConfig.WEATHER_E_INIT.getAbsolutePath());
        executeSql(SQLRunnerConfig.WEATHER_T_INIT.getAbsolutePath());
        executeSql(SQLRunnerConfig.WEATHER_L_INIT.getAbsolutePath());

        executeSql(SQLRunnerConfig.IGC_E_INIT.getAbsolutePath());
        executeSql(SQLRunnerConfig.IGC_T_INIT.getAbsolutePath());
        executeSql(SQLRunnerConfig.IGC_L_INIT.getAbsolutePath());

        executeSql(SQLRunnerConfig.COURSE_E_INIT.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_T_INIT.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_L_INIT.getAbsolutePath());
    }

    public static void runETL() {
        // ELT - IGC
        executeSql(SQLRunnerConfig.IGC_E.getAbsolutePath());
        executeSql(SQLRunnerConfig.IGC_T.getAbsolutePath());
        executeSql(SQLRunnerConfig.IGC_L.getAbsolutePath());

        // ETL - weather
        executeSql(SQLRunnerConfig.WEATHER_E.getAbsolutePath());
        executeSql(SQLRunnerConfig.WEATHER_T.getAbsolutePath());
        executeSql(SQLRunnerConfig.WEATHER_L.getAbsolutePath());

        // ETL - DWH Course
        executeSql(SQLRunnerConfig.COURSE_E_MEMBER.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_E_FLIGHT.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_T_MEMBER.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_T_FLIGHT.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_L_MEMBER.getAbsolutePath());
        executeSql(SQLRunnerConfig.COURSE_L_FLIGHT.getAbsolutePath());
    }

    public static void runAfterETL() {
        executeSql(SQLRunnerConfig.IGC_AFTER_ETL.getAbsolutePath());
    }

    private static void executeSql(String sqlFilePath) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("cmd.exe /c echo exit | sqlplus -S " + DatabaseConfig.INSTANCE.getUSERNAME() + "/"
                    + DatabaseConfig.INSTANCE.getPASSWORD() + "@" + DatabaseConfig.INSTANCE.getSID() + " @" + sqlFilePath);

//            Uncomment below to see output from sql file
            BufferedReader bri = new BufferedReader
                    (new InputStreamReader(p.getInputStream()));
            BufferedReader bre = new BufferedReader
                    (new InputStreamReader(p.getErrorStream()));
            while ((line = bri.readLine()) != null) {
                System.out.println(line);
            }
            bri.close();
            while ((line = bre.readLine()) != null) {
                System.out.println(line);
            }
            bre.close();

            p.waitFor();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}
