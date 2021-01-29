package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.PaymentType;
import wantsome.project.Model.Product;

import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;
import java.util.stream.Collectors;

import static wantsome.project.DBManager.getConnection;

public interface CartDAO {

    List<Product> products = new ArrayList<>();
    List<ProductDTO> productDTOList = new ArrayList<>();

    static Map<ProductDTO, Long> frequency(List<ProductDTO> products) {
        return products.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    static void remove(ProductDTO p, List<ProductDTO> productDTOList) {
        productDTOList.remove(p);
    }

    static void addInCart(ProductDTO p, Integer noOfItems) {
        for (int i = 0; i < noOfItems; i++) {
            products.add(p.toProduct());
            productDTOList.add(p);
        }
    }

    static Integer getTotalPrice() {
        Integer sum = 0;
        for (ProductDTO p : productDTOList) {
            sum += p.getPrice();
        }
        return sum;
    }

    static void sendOrder(UserDTO userDTO, CartDTO cartDTO) throws SQLException {
        String query = "insert into carts (user_id, type_of_payment,total_price) values (?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, userDTO.getId());
            statement.setString(2, cartDTO.getPaymentType().name());
            statement.setInt(3, getTotalPrice());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating cart failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    cartDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating cart failed, no ID obtained.");
                }
            }
        }
    }

    static CartDTO getById(Integer id) throws SQLException {
        CartDTO cartDTO = null;
        String query = "select * from carts where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cartDTO = new CartDTO(rs.getInt(1), UserDAO.getById(rs.getInt(2)), PaymentType.valueOf(rs.getString(3)), rs.getString(4), rs.getInt(5));
            }
        }
        return cartDTO;
    }

    static List<CartDTO> getAllOrders(Integer userId) throws SQLException {
        List<CartDTO> cartDTOS = new ArrayList<>();
        CartDTO cartDTO;
        String sql = "SELECT DISTINCT c.id \n" +
                "FROM order_item oi\n" +
                " JOIN  carts c  ON c.id = oi.cart_id \n" +
                " join users u on u.id = c.user_id \n" +
                " join products p on p.id = oi.product_id \n" +
                "  WHERE u.id = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cartDTO = CartDAO.getById(rs.getInt(1));
                cartDTOS.add(cartDTO);
            }
            return cartDTOS;
        }
    }

    static Map<ProductDTO, Integer> getListOrder(Integer id) throws SQLException {
        Map<ProductDTO, Integer> productQuantity = new HashMap<>();
        String sql = "SELECT  p.id ,oi.quantity \n" +
                "FROM order_item oi\n" +
                " JOIN  carts c  ON c.id = oi.cart_id \n" +
                " join users u on u.id = c.user_id \n" +
                " join products p on p.id = oi.product_id \n" +
                "  WHERE c.id = ?;";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                ProductDTO productDTO = ProductDAO.getById(rs.getInt(1));
                productQuantity.put(productDTO, rs.getInt(2));
            }
            return productQuantity;
        }
    }

    static Integer getTotalPriceForCart(Integer id) throws SQLException {
        String sql = "Select c.total_price from carts c where id = ? ";
        Integer result = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
            return result;
        }
    }

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
