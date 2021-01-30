package wantsome.project.DAO;

import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.UserType;

import java.sql.*;


import static wantsome.project.DBManager.getConnection;

public class UserDAOImpl implements UserDAO {
    @Override
    public void create(UserDTO userDTO) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public UserDTO get(UserDTO user, PreparedStatement preparedStatement) {
        ResultSet rs;
        try {
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                user = new UserDTO(rs.getInt(1), rs.getString("email"), rs.getString("password"), UserType.valueOf(rs.getString("user_type")), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public UserDTO getUser(String email) {
        UserDTO user = null;
        String query = "select * from users where email = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            user = get(user, preparedStatement);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    @Override
    public void update(UserDTO userDTO, Integer id) {
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
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public UserDTO getById(Integer id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        UserDTO userDTO = null;
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                userDTO = new UserDTO(rs.getInt(1), rs.getString("email"), rs.getString("password"), UserType.valueOf(rs.getString("user_type")), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userDTO;
    }


}


