package wantsome.project.DAO;

import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.UserType;

import java.sql.*;

import static wantsome.project.DBManager.getConnection;

public interface UserDAO {

    static void addCustomer(UserDTO userDTO) throws SQLException {
        String query = "Insert into users (email, password, user_type, name, address, phone_number) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, userDTO.getEmail());
            statement.setString(2, userDTO.getPassword());
            statement.setString(3, String.valueOf(userDTO.getUserType()));
            statement.setString(4, userDTO.getName());
            statement.setString(5, userDTO.getAddress());
            statement.setString(6, userDTO.getPhoneNumber());
            int affectedRows = statement.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userDTO.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    static UserDTO getUserDTO(UserDTO user, PreparedStatement preparedStatement) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            user = new UserDTO(rs.getInt(1), rs.getString("email"), rs.getString("password"), UserType.valueOf(rs.getString("user_type")), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"));
        }
        return user;
    }

    static UserDTO getUser(String email) throws SQLException {
        UserDTO user = null;
        String query = "select * from users where email = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            return getUserDTO(user, preparedStatement);
        }
    }

    static void update(UserDTO userDTO, Integer id) throws SQLException {
        String query = "update users set email = ?, password = ?, user_type = ?, name = ?, address = ?, phone_number = ? where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userDTO.getEmail());
            preparedStatement.setString(2, userDTO.getPassword());
            preparedStatement.setString(3, userDTO.getUserType().name());
            preparedStatement.setString(4, userDTO.getName());
            preparedStatement.setString(5, userDTO.getAddress());
            preparedStatement.setString(6, userDTO.getPhoneNumber());
            preparedStatement.setInt(7, id);
            preparedStatement.executeUpdate();
        }
    }

    static UserDTO getById(Integer id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        UserDTO userDTO = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userDTO = new UserDTO(rs.getInt(1), rs.getString("email"), rs.getString("password"), UserType.valueOf(rs.getString("user_type")), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"));
            }
            return userDTO;
        }
    }

    //methods for populating database when application starts for first time
    static void insertIfNotExistsAdmin() {
        String query = "INSERT OR REPLACE INTO users(id,email,password,user_type,name,address,phone_number) \n" +
                "values('1','admin@gmail.com','$2a$12$kjtFtIxFnK1GJBxbeExWoudKOzfGQ.m1PciPLQAL9jYJBnSpf34W.', 'ADMIN','Andrei','My address','0751760911');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    static void insertIfNotExistsCustomer() {
        String query = "INSERT OR REPLACE INTO users(id,email,password,user_type,name,address,phone_number) \n" +
                "values('2','andrei@gmail.com','$2a$12$4nIw1DdTORZgzF4JudFO9OqwWuv8hjuS0ezCLmID55dW2t.Dh1/PG', 'CUSTOMER','Andrei','My address','0751760911');";
        try (Connection connection = getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

