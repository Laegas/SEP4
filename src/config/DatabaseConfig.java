package config;

public class DatabaseConfig {
    public static final String USERNAME = "root";
    public static final String PASSWORD = "password";
    public static final String SID = "ORCL";
    public static final String CONNECTION_STRING = "jdbc:oracle:thin:@localhost:1521:" + SID.toLowerCase();
}
