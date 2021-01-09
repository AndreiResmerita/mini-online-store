package wantsome.project.DAO;

import wantsome.project.Controller.CartController;
import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.UserDTO;
import java.sql.*;
import java.util.List;

import static wantsome.project.DBManager.getConnection;

public class CartDAOImpl implements CartDAO {


    @Override
    public void sendOrder(UserDTO userDTO, CartDTO cartDTO) throws SQLException {
        String query = "insert into cart (user_id, type_of_payment,total_price) values (?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, userDTO.getId());
            statement.setString(2, cartDTO.getPaymentType().name());
            statement.setInt(3, CartController.getTotalPrice());

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

    @Override
    public List<CartDTO> getAllCartProducts(CartDTO cartDTO, UserDTO userDTO) throws SQLException {
        return null;
    }


}
