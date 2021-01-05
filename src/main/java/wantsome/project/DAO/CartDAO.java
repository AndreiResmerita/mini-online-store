package wantsome.project.DAO;

import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;

import java.sql.SQLException;
import java.util.List;

public interface CartDAO {
   public void addCart(CartDTO cartDTO) throws SQLException;
    public List<CartDTO> getAllCartProducts(CartDTO cartDTO, UserDTO userDTO) throws SQLException;

}
