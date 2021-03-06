package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.OrderItemDTO;
import wantsome.project.DTO.ProductDTO;

import java.sql.*;

import static wantsome.project.DBManager.getConnection;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public void insert(CartDTO cartDTO, ProductDTO productDTO, Long quantity, OrderItemDTO itemDTO) {
        String query = "insert into order_item (cart_id, product_id,quantity) values (?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, cartDTO.getId());
            statement.setInt(2, productDTO.getId());
            statement.setInt(3, quantity.intValue());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating cart failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    itemDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating cart failed, no ID obtained.");
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
