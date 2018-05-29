package config;

public class DatabaseConfig {
    public static final String USERNAME = "DVK";
    public static final String PASSWORD = "svx28bag";
    public static final String SID = "DVK";
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
