package wantsome.project.Controller;

import java.io.File;
import java.nio.file.Path;
import static wantsome.project.web.RequestUtil.RequestUtil.*;
import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.ProductDAO;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;
import javax.servlet.MultipartConfigElement;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import static wantsome.project.DAO.CartDAOImpl.*;

public class ProductsController {


    public static Route getProductsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();
        model.put("cs", productDTOList.size());
        model.put("products", productDAO.getAllProducts());
        return SparkUtil.render(request, model, Paths.Template.PRODUCTS);
    };

    public static Route handleProductPost = (Request request, Response response) -> {
        ProductDAO productDAO = new ProductDAOImpl();
        File uploadDir = new File("upload");
        Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
        request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
        Product product = new Product(tempFile.getFileName(), ProductType.valueOf(request.queryParams("product_type")),
                request.queryParams("product_name"), request.queryParams("description"),
                Integer.parseInt(request.queryParams("price")),
                Integer.parseInt(request.queryParams("stock")));
        Files.copy(request.raw().getPart("img").getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
        ProductDTO productDTO = new ProductDTO(product);
        productDAO.addProduct(productDTO);
        response.redirect(Paths.Web.PRODUCTS);
        return null;
    };

    public static Route getEachProductsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();
        model.put("cs", productDTOList.size());
        ProductDTO productDTO = productDAO.getById(Integer.parseInt(getParamsProdId(request)));
        model.put("product", productDTO);
        return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
    };

}
