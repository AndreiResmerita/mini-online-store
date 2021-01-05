package wantsome.project.DAO;

import com.google.common.collect.ImmutableList;
import wantsome.project.Controller.ProductsController;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;
import wantsome.project.Model.UserType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static wantsome.project.DBManager.getConnection;

public class ProductDAOImpl implements ProductDAO {


    private final List<ProductDTO> products = ImmutableList.of(new ProductDTO(3, Path.of("upload/1646675099306288925"), ProductType.SMARTPHONE, "Samsung S10", "Another WOW Telephone", 1000, 100));


    public ProductDTO getRandomProduct() {
        return products.get(new Random().nextInt(products.size()));
    }


    @Override
    public void addProduct(ProductDTO productDTO) throws SQLException {
        String query = "Insert into products (img, product_type, product_name, description, price, stock) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, String.valueOf(productDTO.getImg()));
            statement.setString(2, productDTO.getProductType().name());
            statement.setString(3, productDTO.getProductName());
            statement.setString(4, productDTO.getDescription());
            statement.setInt(5, productDTO.getPrice());
            statement.setInt(6, productDTO.getStock());


            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    productDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    @Override
    public void update(ProductDTO productDTO, Integer id) throws SQLException {
        String query = "update products set  product_name = ?, description = ?, price = ?, stock = ? where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, productDTO.getProductName());
            preparedStatement.setString(2, productDTO.getDescription());
            preparedStatement.setInt(3, productDTO.getPrice());
            preparedStatement.setInt(4, productDTO.getStock());
            preparedStatement.setInt(5, productDTO.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void deleteProduct(Integer id) {
        String sql = "DELETE FROM products WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ProductDTO getById(Integer id) throws SQLException {

        ProductDTO productDTO = null;

        String query = "select * from products where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                productDTO = new ProductDTO(rs.getInt(1), Path.of("upload/" + rs.getString("img")).getFileName().toAbsolutePath(), ProductType.valueOf(rs.getString("product_type")), rs.getString("product_name"), rs.getString("description"), rs.getInt("price"), rs.getInt("stock"));
            }
            return productDTO;
        }
    }

    @Override
    public List<ProductDTO> getAllProducts() throws SQLException {
        String query = "select * from products ";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<ProductDTO> productDTOArrayList = new ArrayList<>();
            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO(rs.getInt("id"),
                        Path.of(rs.getString("img")),
                        ProductType.valueOf(rs.getString("product_type")),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getInt("stock"));
                productDTOArrayList.add(productDTO);
            }
            return productDTOArrayList;
        }
    }

}
