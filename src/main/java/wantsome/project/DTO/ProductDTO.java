package wantsome.project.DTO;

import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;
import java.nio.file.Path;


public class ProductDTO {
    private final Path img;
    private Integer id;
    private final ProductType productType;
    private final String productName;
    private final String description;
    private final Integer price;
    private final Integer stock;


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

}
