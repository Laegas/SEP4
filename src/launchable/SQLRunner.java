package launchable;

import config.DatabaseConfig;

public class SQLRunner {

    public static void main(String[] args) {

    }

    private static void executeSql(String sqlFilePath) {
        try {
            String line;
            Process p = Runtime.getRuntime().exec("cmd.exe /c echo exit | sqlplus -S " + DatabaseConfig.USERNAME + "/"
                    + DatabaseConfig.PASSWORD + "@" + DatabaseConfig.SID + " @" + sqlFilePath);
        /*
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
        */
            p.waitFor();
        }
        catch (Exception err) {
            err.printStackTrace();
        }
    }
}
