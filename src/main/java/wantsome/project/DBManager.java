package wantsome.project;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.Properties;

public class DBManager {
    public static Connection getConnection() throws SQLException {
        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);
        Properties properties = new Properties();
        properties.put("url", "jdbc:sqlite:D:\\SQLite Project\\Aici.db"); // put here the location of the new db file
        return DriverManager.getConnection(properties.getProperty("url"));
    }
    //methods for creating tables when application starts for first time
    public static void createTableIfNotExistUsers() {
        String sql = "CREATE TABLE IF NOT EXISTS users (\n" +
                " id integer primary key autoincrement, \n" +
                " email varchar(50) NOT NULL UNIQUE,\n" +
                " password VARCHAR(20) NOT NULL,\n" +
                " user_type varchar(20),\n" +
                " name varchar(50),\n" +
                " address varchar(100),\n" +
                " phone_number varchar(20));";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableIfNotExistProducts() {
        String sql = "CREATE TABLE IF NOT EXISTS products (\n" +
                " id       integer primary key autoincrement,\n" +
                " img varchar(100),\n" +
                " product_type     varchar(50),\n" +
                " product_name varchar(50),\n" +
                " description varchar(50) NOT NULL,\n" +
                " price int,\n" +
                " stock int\n" +
                " );";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableIfNotExistCarts() {
        String sql = "CREATE TABLE IF NOT EXISTS carts (\n" +
                " id     integer primary key autoincrement,\n" +
                " user_id integer references users(id),\n" +
                " type_of_payment varchar(20),\n" +
                " order_date DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                " total_price int\n" +
                " );";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void createTableIfNotExistOrderItem() {
        String sql = "CREATE TABLE IF NOT EXISTS order_item ( \n" +
                " id         integer primary key autoincrement,\n" +
                " cart_id     int references cart(id),\n" +
                " product_id int references products(id),\n" +
                " quantity int\n" +
                " );";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
