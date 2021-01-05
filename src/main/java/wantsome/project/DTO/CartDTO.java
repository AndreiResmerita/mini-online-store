package wantsome.project.DTO;

import wantsome.project.Model.Cart;
import wantsome.project.Model.PaymentType;

public class CartDTO {
    private Integer id;
    private UserDTO userDTO;
    private PaymentType paymentType;
    private String date;
    private Integer totalprice;

    public CartDTO(Integer id, UserDTO userDTO, PaymentType paymentType, String date, Integer totalprice) {
        this.id = id;
        this.userDTO = userDTO;
        this.paymentType = paymentType;
        this.date = date;
        this.totalprice = totalprice;
    }

    public CartDTO(Cart cart) {
        this.paymentType = cart.getPaymentType();
        this.date = cart.getDate();
        this.totalprice = cart.getTotalprice();
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
                ", date='" + date + '\'' +
                ", totalprice=" + totalprice +
                '}';
    }
}
