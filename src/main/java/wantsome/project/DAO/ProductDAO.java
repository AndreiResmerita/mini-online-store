package wantsome.project.DAO;

import wantsome.project.DTO.ProductDTO;
import wantsome.project.Model.ProductType;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static wantsome.project.DBManager.getConnection;

public interface ProductDAO {

    ProductDTO getRandomProduct();

    void create(ProductDTO productDTO);

    void update(ProductDTO productDTO);

    void updateStockOnly(Long quantity, Integer id);

    void delete(Integer id);

    ProductDTO get(Integer id);

    List<ProductDTO> getAll();

    List<ProductDTO> getAllForJSON();

    ProductDTO getMostBought();

    //methods for populating database when application starts for first time
    static void insertIfNotExistsProduct1() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('1','6903572620603954145','TV', 'QLED Smart 4K TV Q90T','Vezi fiecare detaliu în orice unghi. Ultra Viewing Angle îți oferă o imagine completă, chiar și atunci când privești la televizor din lateral. Acum fiecare loc din casă este cel mai bun.','9000','200');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct2() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('2','16970925616874275167','PC', 'Desktop PC MYRIA Digital V26','Intel Core i13, Nvidia Graphic Card','7360','97');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct3() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('3','3362912836627949437','LAPTOP', 'Lenovo Legion 5','Procesor AMD Ryzen 5 4600H pana la 4.00 GHz, 15.6, Full HD, 144Hz','4500','130');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct4() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('4','1887509907220935446','LAPTOP', 'Laptop ASUS ProArt StudioBook One','Procesor Intel® Core™ i9-9980HK pana la 5GHz, 15.6 Full HD, 64GB, 1TB SSD','55000','0');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct5() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('5','5512633219026565486','SMARTPHONE', 'Smartphone Iphone 12 PRO','Placat cu platina si piele de crocodil gri','16550','5');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
