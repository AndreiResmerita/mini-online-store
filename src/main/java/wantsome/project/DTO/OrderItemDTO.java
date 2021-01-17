package wantsome.project.DTO;

public class OrderItemDTO {
    private Integer id;
    private CartDTO cart;
    private ProductDTO product;
    private Integer quantity;

    public OrderItemDTO(Integer id, CartDTO cart, ProductDTO product, Integer quantity) {
        this.id = id;
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
    }

    public OrderItemDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CartDTO getCart() {
        return cart;
    }

    public void setCart(CartDTO cart) {
        this.cart = cart;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItemDTO{" +
                "id=" + id +
                ", cart=" + cart +
                ", product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
