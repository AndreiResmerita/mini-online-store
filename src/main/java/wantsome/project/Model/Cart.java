package wantsome.project.Model;

public class Cart {
    private final PaymentType paymentType;

    private final Integer totalprice;


    public Cart(PaymentType paymentType, Integer totalprice) {
        this.paymentType = paymentType;
        this.totalprice = totalprice;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public Integer getTotalprice() {
        return totalprice;
    }


}

