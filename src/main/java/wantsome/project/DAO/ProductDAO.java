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

    static List<ProductDTO> getAllProductsJson() throws SQLException {
        ArrayList<ProductDTO> productDTOArrayList = new ArrayList<>();
        String query = "SELECT p.id,p.product_type ,p.product_name ,p.description ,p.price ,p.stock ,SUM(oi.quantity) \n" +
                "from products p\n" +
                "left join order_item oi on p.id = oi.product_id \n" +
                "GROUP BY p.id ;";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO(rs.getInt(1),
                        ProductType.valueOf(rs.getString(2)),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getInt(7));
                productDTOArrayList.add(productDTO);
            }
            return productDTOArrayList;
        }
    }

    static ProductDTO getMostBoughtProduct() throws SQLException {
        ProductDTO productDTO = null;
        String query = "SELECT p2.id,p2.img ,p2.product_type ,p2.product_name,p2.description ,p2.price ,p2.stock \n" +
                "FROM carts s\n" +
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

    //methods for populating database when application starts for first time
    static void insertIfNotExistsProduct1() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('1','6903572620603954145','TV', 'QLED Smart 4K TV Q90T','Vezi fiecare detaliu în orice unghi. Ultra Viewing Angle îți oferă o imagine completă, chiar și atunci când privești la televizor din lateral. Acum fiecare loc din casă este cel mai bun.','9000','200');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct2() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('2','16970925616874275167','PC', 'Desktop PC MYRIA Digital V26','Intel Core i13, Nvidia Graphic Card','7360','97');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct3() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('3','3362912836627949437','LAPTOP', 'Lenovo Legion 5','Procesor AMD Ryzen 5 4600H pana la 4.00 GHz, 15.6, Full HD, 144Hz','4500','130');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct4() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('4','1887509907220935446','LAPTOP', 'Laptop ASUS ProArt StudioBook One','Procesor Intel® Core™ i9-9980HK pana la 5GHz, 15.6 Full HD, 64GB, 1TB SSD','55000','0');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsProduct5() {
        String query = "INSERT OR REPLACE INTO products(id,img,product_type,product_name,description,price,stock) \n" +
                "values('5','5512633219026565486','SMARTPHONE', 'Smartphone Iphone 12 PRO','Placat cu platina si piele de crocodil gri','16550','5');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
