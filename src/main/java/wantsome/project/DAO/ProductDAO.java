package wantsome.project.DAO;

import wantsome.project.DTO.ProductDTO;
import wantsome.project.Model.ProductType;

import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static wantsome.project.DBManager.getConnection;

public interface ProductDAO {

    static ProductDTO getRandomProduct() throws SQLException {
        return getAllProducts().get(new Random().nextInt(getAllProducts().size()));
    }

    static void addProduct(ProductDTO productDTO) throws SQLException {
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
                throw new SQLException("Creating product failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    productDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating product failed, no ID obtained.");
                }
            }
        }
    }

    static void update(ProductDTO productDTO) throws SQLException {
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

    static void updateStock(Long quantity, Integer id) throws SQLException {
        String query = "update products set stock = ? where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quantity.intValue());
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
        }
    }

    static void deleteProduct(Integer id) {
        String sql = "DELETE FROM products WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    static ProductDTO getById(Integer id) throws SQLException {
        ProductDTO productDTO = null;
        String query = "select * from products where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                productDTO = new ProductDTO(rs.getInt(1), Path.of(rs.getString("img")).getFileName(), ProductType.valueOf(rs.getString("product_type")), rs.getString("product_name"), rs.getString("description"), rs.getInt("price"), rs.getInt("stock"));
            }
            return productDTO;
        }
    }

    static List<ProductDTO> getAllProducts() throws SQLException {
        ArrayList<ProductDTO> productDTOArrayList = new ArrayList<>();
        String query = "select * from products ";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
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

    static ProductDTO getMostBoughtProduct() throws SQLException {
        ProductDTO productDTO = null;
        String query = "SELECT p2.id,p2.img ,p2.product_type ,p2.product_name,p2.description ,p2.price ,p2.stock \n" +
                "FROM cart s\n" +
                " JOIN  users u2  ON s.user_id = u2.id \n" +
                " JOIN products p2,order_item oi ON p2.id = oi.product_id\n" +
                " GROUP BY oi.product_id \n" +
                " ORDER BY COUNT(oi.quantity) ASC \n" +
                " LIMIT 1;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                productDTO = new ProductDTO(rs.getInt("id"),
                        Path.of(rs.getString("img")),
                        ProductType.valueOf(rs.getString("product_type")),
                        rs.getString("product_name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getInt("stock"));
            }
            return productDTO;
        }
    }
}
