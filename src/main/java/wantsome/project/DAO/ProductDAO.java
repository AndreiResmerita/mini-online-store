package wantsome.project.DAO;

import wantsome.project.DTO.ProductDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ProductDAO {

    void addProduct(ProductDTO productDTO) throws SQLException;

    public void update(ProductDTO productDTO) throws SQLException;
    public void updateStock(Integer quantity,ProductDTO productDTO) throws SQLException;

    public void deleteProduct(Integer id);

    public ProductDTO getById(Integer id) throws SQLException;

    public List<ProductDTO> getAllProducts() throws SQLException;

}
