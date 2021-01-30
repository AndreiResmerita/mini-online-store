package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.PaymentType;
import wantsome.project.Model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static wantsome.project.DBManager.getConnection;

public interface CartDAO {

    List<Product> products = new ArrayList<>();
    List<ProductDTO> productDTOList = new ArrayList<>();

    void addInCart(ProductDTO p, Integer noOfItems);

    void createOrder(UserDTO userDTO, CartDTO cartDTO);

    CartDTO getById(Integer id);

    List<CartDTO> getAllOrders(Integer userId);

    Map<ProductDTO, Integer> getListOrder(Integer id);

    Integer getTotalPriceForCart(Integer id);

    Integer getTotalPrice();

    void remove(ProductDTO p, List<ProductDTO> productDTOList);

    Map<ProductDTO, Long> frequency(List<ProductDTO> products);

    //methods for populating carts table when application starts for first time
    static void insertIfNotExistsCart1() {
        String query = "INSERT OR REPLACE INTO carts(id,user_id,type_of_payment,order_date,total_price) \n" +
                "values('1','2','CARD', '2021-01-18 11:12:51','14720');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsCart2() {
        String query = "INSERT OR REPLACE INTO carts(id,user_id,type_of_payment,order_date,total_price) \n" +
                "values('2','2','CASHONDELIVERY', '2021-01-18 11:53:32','67650');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsCart3() {
        String query = "INSERT OR REPLACE INTO carts(id,user_id,type_of_payment,order_date,total_price) \n" +
                "values('3','2','CASHONDELIVERY', '2021-01-18 16:56:18','7360');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
