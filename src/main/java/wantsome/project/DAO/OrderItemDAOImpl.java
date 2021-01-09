package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.OrderItemDTO;

import java.sql.SQLException;
import java.util.List;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public void insert() {

    }

    @Override
    public OrderItemDTO getMostBoughtProduct(OrderItemDTO orderItemDTO) throws SQLException {
        return null;
    }

    @Override
    public List<CartDTO> getAllCarts(CartDTO cartDTO) {
        return null;
    }
}
