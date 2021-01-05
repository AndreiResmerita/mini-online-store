package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.OrderItemDTO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.User;

import java.sql.SQLException;
import java.util.List;

public interface OrderItemDAO {
    public OrderItemDTO getMostBoughtProduct(OrderItemDTO orderItemDTO) throws SQLException;
    public List<CartDTO> getAllCarts(CartDTO cartDTO);
}
