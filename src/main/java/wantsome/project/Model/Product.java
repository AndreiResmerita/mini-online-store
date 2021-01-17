package wantsome.project.Model;

import java.math.BigDecimal;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class Product {
    private Path img;
    private ProductType productType;
    private String productName;
    private String description;
    private Integer price;
    private Integer stock;

    public Product(ProductType productType, String productName, String description, Integer price, Integer stock) {
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public Product(Path img, ProductType productType, String productName, String description, Integer price, Integer stock) {
        this.img = img.getFileName();
        this.productType = productType;
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.stock = stock;
        BigDecimal bd = new BigDecimal(price);

        NumberFormat formatter = NumberFormat.getInstance(new Locale("en_US"));

        formatter.format(bd.longValue());

    }

    public Product() {

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

    public String numberFormat() {
        Integer num = price;
        DecimalFormat formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return formatter.format(num.longValue());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getProductType() == product.getProductType() && Objects.equals(getProductName(), product.getProductName()) && Objects.equals(getDescription(), product.getDescription()) && Objects.equals(getPrice(), product.getPrice()) && Objects.equals(getStock(), product.getStock());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProductType(), getProductName(), getDescription(), getPrice(), getStock());
    }
}

