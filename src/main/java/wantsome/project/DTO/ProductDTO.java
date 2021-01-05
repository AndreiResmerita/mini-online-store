package wantsome.project.DTO;

import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;

import java.nio.file.Path;


public class ProductDTO {
    private Path img;
    private Integer id;
    private ProductType productType;
    private String productName;
    private String description;
    private Integer price;
    private Integer stock;


    public ProductDTO(Product product) {
        this.img = product.getImg().getFileName();
        this.productType = product.getProductType();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
    }

    public ProductDTO(Integer id, Path img, ProductType productType, String productName, String description, Integer price, Integer stock) {
        this.id = id;
        this.img = img.getFileName();
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Path getImg() {
        return img.getFileName();
    }

    public void setImg(Path img) {
        this.img = img.getFileName();
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

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", productType=" + productType +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", img='" + img + '\'' +
                '}';
    }
}
