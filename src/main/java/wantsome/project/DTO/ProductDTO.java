package wantsome.project.DTO;

import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;
import java.nio.file.Path;
import java.util.Objects;


public class ProductDTO {
    private  Path img;
    private Integer id;
    private  ProductType productType;
    private  String productName;
    private  String description;
    private  Integer price;
    private  Integer stock;


    public ProductDTO(Product product) {
        this.img = product.getImg();
        this.productType = product.getProductType();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }

    public ProductDTO(Integer id, Path img, ProductType productType, String productName, String description, Integer price, Integer stock) {
        this.id = id;
        this.img = img;
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public ProductDTO() {

    }

    public Path getImg() {
        return img.getFileName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ProductType getProductType() {
        return productType;
    }

    public String getProductName() {
        return productName;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Product toProduct(){
        Product product = new Product();
        product.setProductType(this.productType);
        product.setProductName(this.productName);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setStock(this.stock);
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductDTO)) return false;
        ProductDTO that = (ProductDTO) o;
        return getProductType() == that.getProductType() && Objects.equals(getProductName(), that.getProductName()) && Objects.equals(getDescription(), that.getDescription()) && Objects.equals(getPrice(), that.getPrice()) && Objects.equals(getStock(), that.getStock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductType(), getProductName(), getDescription(), getPrice(), getStock());
    }
}
