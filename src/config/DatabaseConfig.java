package config;

public class DatabaseConfig {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "password";
    public static final String SID = "orcl";
    public static final String CONNECTION_STRING = "jdbc:oracle:thin:@localhost:1521:" + SID.toLowerCase();

    public static String getSID() {
        return SID;
    }

    public static final DatabaseConfig INSTANCE = new DatabaseConfig();

    public String getCONNECTION_STRING() {
        return CONNECTION_STRING;
    }

    public static String getUSERNAME() {
        return USERNAME;
    }

    public static String getPASSWORD() {
        return PASSWORD;
    }
}
