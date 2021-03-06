package wantsome.project.DTO;

import wantsome.project.Model.User;
import wantsome.project.Model.UserType;

import static wantsome.project.Model.User.hashPassword;

public class UserDTO {
    private Integer id;
    private String email;
    private String password;
    private UserType userType;
    private String name;
    private String address;
    private String phoneNumber;

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.password = hashPassword(user.getPassword());
        this.userType = user.getUserType();
        this.name = user.getName();
        this.address = user.getAddress();
        this.phoneNumber = user.getPhoneNumber();
    }

    public UserDTO(Integer id, String email, String password, UserType userType, String name, String address, String phoneNumber) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
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


    public String getPassword() {
        return password;
    }


    public UserType getUserType() {
        return userType;
    }


    public String getName() {
        return name;
    }


    public String getAddress() {
        return address;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User toUser(){
        User user = new User();
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setUserType(this.userType);
        user.setName(this.name);
        user.setAddress(this.address);
        user.setPhoneNumber(this.phoneNumber);
        return user;
    }

}
