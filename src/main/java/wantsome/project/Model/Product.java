package wantsome.project.Model;

import java.nio.file.Path;

public class Product {
    private Path img;
    private ProductType productType;
    private String productName;
    private String description;
    private Integer price;
    private Integer stock;


    public Product(Path img, ProductType productType, String productName, String description, Integer price, Integer stock) {
        this.img = img.getFileName();
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;

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

    public Path getImg() {
        return img.getFileName();
    }

    public void setImg(Path img) {
        this.img = img;
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
        return "Product{" +
                "productType=" + productType +
                ", productName='" + productName + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", Image='" + img + '\'' +
                '}';
    }
}

