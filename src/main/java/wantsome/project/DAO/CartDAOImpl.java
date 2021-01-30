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

public class CartDAOImpl implements CartDAO {

    private static UserDAO userDAO = new UserDAOImpl();
    private static ProductDAO productDAO = new ProductDAOImpl();

    @Override
    public Map<ProductDTO, Long> frequency(List<ProductDTO> products) {
        return products.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    }

    @Override
    public void remove(ProductDTO p, List<ProductDTO> productDTOList) {
        productDTOList.remove(p);
    }

    @Override
    public void addInCart(ProductDTO p, Integer noOfItems) {
        for (int i = 0; i < noOfItems; i++) {
            products.add(p.toProduct());
            productDTOList.add(p);
        }
    }

    @Override
    public Integer getTotalPrice() {
        Integer sum = 0;
        for (ProductDTO p : productDTOList) {
            sum += p.getPrice();
        }
        return sum;
    }

    @Override
    public void createOrder(UserDTO userDTO, CartDTO cartDTO) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public CartDTO getById(Integer id) {
        CartDTO cartDTO = null;
        String query = "select * from carts where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                cartDTO = new CartDTO(rs.getInt(1), userDAO.getById(rs.getInt(2)), PaymentType.valueOf(rs.getString(3)), rs.getString(4), rs.getInt(5));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllOrders(Integer userId) {
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
                cartDTO = getById(rs.getInt(1));
                cartDTOS.add(cartDTO);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return cartDTOS;
    }

    @Override
    public Map<ProductDTO, Integer> getListOrder(Integer id) {
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
                ProductDTO productDTO = productDAO.get(rs.getInt(1));
                productQuantity.put(productDTO, rs.getInt(2));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productQuantity;
    }

    @Override
    public Integer getTotalPriceForCart(Integer id) {
        String sql = "Select c.total_price from carts c where id = ? ";
        Integer result = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
