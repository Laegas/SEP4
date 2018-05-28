package config;

public class DatabaseConfig {
    public static final String USERNAME = "Laegas";
    public static final String PASSWORD = "1234";
    public static final String SID = "ORCL";
    public static final String CONNECTION_STRING = "jdbc:oracle:thin:@localhost:1521:" + SID.toLowerCase();
}
