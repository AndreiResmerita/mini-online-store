package wantsome.project.DAO;

import wantsome.project.DTO.UserDTO;

import java.sql.SQLException;

public interface UserDAO {


    public void addCustomer(UserDTO userDTO) throws SQLException;

    public UserDTO getUser(String email, String password) throws SQLException;


    public void update(UserDTO userDTO) throws SQLException;

    public void delete(Integer id) throws SQLException;
}
