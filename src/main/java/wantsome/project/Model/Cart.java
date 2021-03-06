package wantsome.project.Model;

import java.util.Objects;

public class Cart {

    private  PaymentType paymentType;
    private  Integer totalprice;


    public Cart(PaymentType paymentType, Integer totalprice) {
        this.paymentType = paymentType;
        this.totalprice = totalprice;
    }

    public Cart() {

    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Integer getTotalprice() {
        return totalprice;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public void setTotalprice(Integer totalprice) {
        this.totalprice = totalprice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cart)) return false;
        Cart cart = (Cart) o;
        return getPaymentType() == cart.getPaymentType() && Objects.equals(getTotalprice(), cart.getTotalprice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPaymentType(), getTotalprice());
    }
}

