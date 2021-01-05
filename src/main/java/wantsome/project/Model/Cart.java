package wantsome.project.Model;

public class Cart {
    private PaymentType paymentType;
    private String date;
    private Integer totalprice;


    public Cart(PaymentType paymentType, String date, Integer totalprice) {
        this.paymentType = paymentType;
        this.date = date;
        this.totalprice = totalprice;
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
        return "Cart{" +
                "paymentType=" + paymentType +
                ", date='" + date + '\'' +
                ", totalprice=" + totalprice +
                '}';
    }
}

