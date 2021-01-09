package wantsome.project.DTO;

import wantsome.project.Model.Cart;
import wantsome.project.Model.PaymentType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartDTO {

    private Integer id;
    private UserDTO userDTO;
    private PaymentType paymentType;

    private Integer totalprice;


    public CartDTO(Integer id, UserDTO userDTO, PaymentType paymentType, Integer totalprice) {
        this.id = id;
        this.userDTO = userDTO;
        this.paymentType = paymentType;
        this.totalprice = totalprice;
    }

    public CartDTO(Cart cart) {
        this.paymentType = cart.getPaymentType();
        this.totalprice = cart.getTotalprice();
    }

    public CartDTO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public String toString() {
        return "CartDTO{" +
                "id=" + id +
                ", userDTO=" + userDTO +
                ", paymentType=" + paymentType +
                ", totalprice=" + totalprice +
                '}';
    }
}
