package wantsome.project.DTO;

import org.mindrot.jbcrypt.BCrypt;
import wantsome.project.DAO.UserDAOImpl;
import wantsome.project.Model.User;
import wantsome.project.Model.UserType;

public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private UserType userType;
    private String name;
    private String address;
    private Integer phoneNumber;




    public UserDTO(Integer id,User user){
        this.id = id;
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.userType = user.getUserType();
        this.name = user.getName();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
    }
    public UserDTO(User user) {
        this.email = user.getEmail();
        this.password = hashPassword(user.getPassword());
        this.userType = user.getUserType();
        this.name = user.getName();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
    }

    public UserDTO(Integer id, String email, String password, UserType userType, String name, String address, Integer phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public static String hashPassword(String plainTextPassword) {
        String salt = BCrypt.gensalt(12);
        return (BCrypt.hashpw(plainTextPassword, salt));

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public User toUser() {
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setUserType(this.userType);
        user.setName(this.name);
        user.setAddress(this.address);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
