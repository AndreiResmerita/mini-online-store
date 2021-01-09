package wantsome.project.DAO;

import org.mindrot.jbcrypt.BCrypt;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.User;
import wantsome.project.Model.UserType;

import java.sql.*;


import static wantsome.project.DBManager.getConnection;
import static wantsome.project.DTO.UserDTO.hashPassword;

public class UserDAOImpl implements UserDAO {


    @Override
    public void addCustomer(UserDTO userDTO) throws SQLException {
        String query = "Insert into users (email, password, user_type, name, address, phone_number) values (?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query,
                     Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, userDTO.getEmail());
            statement.setString(2, userDTO.getPassword());
            statement.setString(3, String.valueOf(userDTO.getUserType()));
            statement.setString(4, userDTO.getName());
            statement.setString(5, userDTO.getAddress());
            statement.setInt(6, userDTO.getPhoneNumber());

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

    @Override
    public UserDTO getUser(String email, String password) throws SQLException {
        UserDTO user = null;

        String query = "select * from users where email = ? and password = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            return getUserDTO(user, preparedStatement);
        }
    }

    public UserDTO getUserDTO(UserDTO user, PreparedStatement preparedStatement) throws SQLException {
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            user = new UserDTO(rs.getInt(1), rs.getString("email"), rs.getString("password"), UserType.valueOf(rs.getString("user_type")), rs.getString("name"), rs.getString("address"), rs.getInt("phone_number"));
        }
        return user;
    }


    public UserDTO getUser(String email) throws SQLException {
        UserDTO user = null;

        String query = "select * from users where email = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            return getUserDTO(user, preparedStatement);
        }
    }


    @Override
    public void update(UserDTO userDTO) throws SQLException {

        String query = "update users set email = ?, password = ?, user_type = ?, name = ?, address = ?, phone_number = ? where id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, userDTO.getEmail());
            preparedStatement.setString(2, userDTO.getPassword());
            preparedStatement.setString(3, userDTO.getUserType().name());
            preparedStatement.setString(4, userDTO.getName());
            preparedStatement.setString(5, userDTO.getAddress());
            preparedStatement.setInt(6, userDTO.getPhoneNumber());
            preparedStatement.setInt(7, userDTO.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(Integer id){

        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

