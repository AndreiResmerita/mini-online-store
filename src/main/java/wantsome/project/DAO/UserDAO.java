package wantsome.project.DAO;

import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.UserType;

import java.sql.*;

import static wantsome.project.DBManager.getConnection;

public interface UserDAO {

    void create(UserDTO userDTO);

    UserDTO get(UserDTO user, PreparedStatement preparedStatement);

    UserDTO getUser(String email);

    void update(UserDTO userDTO, Integer id);

    UserDTO getById(Integer id);

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

