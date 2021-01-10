package wantsome.project.Model;

public class OrderItem {
    private Integer quantity;

    public OrderItem() {

    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public OrderItem(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "quantity=" + quantity +
                '}';
    }
}
