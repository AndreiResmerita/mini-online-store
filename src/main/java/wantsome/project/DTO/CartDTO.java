package wantsome.project.DTO;

import wantsome.project.Model.Cart;
import wantsome.project.Model.PaymentType;
import wantsome.project.Model.Product;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public String getDate() {
        return date;
    }

    public String dateFormat() {
        String string = date;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
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

    public Cart toCart(){
        Cart cart = new Cart();
        cart.setPaymentType(this.paymentType);
        cart.setTotalprice(this.totalprice);
        return cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartDTO)) return false;
        CartDTO cartDTO = (CartDTO) o;
        return Objects.equals(getId(), cartDTO.getId()) && Objects.equals(getUserDTO(), cartDTO.getUserDTO()) && getPaymentType() == cartDTO.getPaymentType() && Objects.equals(getDate(), cartDTO.getDate()) && Objects.equals(getTotalprice(), cartDTO.getTotalprice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserDTO(), getPaymentType(), getDate(), getTotalprice());
    }
}
