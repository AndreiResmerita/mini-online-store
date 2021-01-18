package wantsome.project;

import org.sqlite.SQLiteConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBManager {
    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        Properties properties = new Properties();
        properties.put("url","jdbc:sqlite:D:\\SQLite Project\\Aici.db");
        return DriverManager.getConnection(properties.getProperty("url"));
    }
}
