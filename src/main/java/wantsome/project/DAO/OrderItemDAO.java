package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.OrderItemDTO;
import wantsome.project.DTO.ProductDTO;

import java.sql.*;

import static wantsome.project.DBManager.getConnection;

public interface OrderItemDAO {

    void insert(CartDTO cartDTO, ProductDTO productDTO, Long quantity, OrderItemDTO itemDTO);

    //methods for populating when application starts for first time
    static void insertIfNotExistsOrderItem1() {
        String query = "INSERT OR REPLACE INTO order_item(id,cart_id,product_id,quantity) \n" +
                "values('1','1','2','2');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsOrderItem2() {
        String query = "INSERT OR REPLACE INTO order_item(id,cart_id,product_id,quantity) \n" +
                "values('2','2','3','4');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsOrderItem3() {
        String query = "INSERT OR REPLACE INTO order_item(id,cart_id,product_id,quantity) \n" +
                "values('3','2','5','3');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsOrderItem4() {
        String query = "INSERT OR REPLACE INTO order_item(id,cart_id,product_id,quantity) \n" +
                "values('4','3','2','1');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
