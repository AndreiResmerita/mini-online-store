package wantsome.project.DTO;

import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;


import java.nio.file.Path;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;


public class ProductDTO {
    private Path img;
    private Integer id;
    private final ProductType productType;
    private String productName;
    private String description;
    private Integer price;
    private Integer stock;
    private Integer noOfSales;


    public ProductDTO(Integer id, Path img, ProductType productType, String productName, String description, Integer price, Integer stock) {
        this.id = id;
        this.img = img;
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public ProductDTO(Integer id, ProductType productType, String productName, String description, Integer price, Integer stock, Integer noOfSales) throws SQLException {
        this.id = id;
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.noOfSales = noOfSales;
    }

    public ProductDTO(Product product) {
        this.img = product.getImg();
        this.productType = product.getProductType();
        this.productName = product.getProductName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.stock = product.getStock();
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
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator(' ');
        formatter.setDecimalFormatSymbols(symbols);
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Product toProduct() {
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

    public String numberFormat() {
        Integer num = price;
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(num.longValue());
    }
}
