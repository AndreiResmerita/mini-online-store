package wantsome.project.web;

import java.io.File;

import java.nio.file.Path;
import spark.*;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.Model.Product;
import wantsome.project.Model.ProductType;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import static spark.Spark.*;


public class Ex {

    public static void main(String[] args) {

        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist

        Spark.externalStaticFileLocation("upload");

        get("/", (req, res) ->
                "<form method='post' enctype='multipart/form-data'>"// note the enctype
                        + "    <input type='file' name='img' accept='.jpg'>" // make sure to call getPart using the same "name" in the post
                       + "<label for=\"product_type\" class=\"my-1 mr-2\" id=\"inlineFormCustomSelectPref\">Type of product</label>\n" +
                        "    <select name=\"product_type\" class=\"custom-select my-1 mr-sm-2\" id=\"inlineFormCustomSelectPref\">\n" +
                        "        <option value=\"PC\">PC</option>\n" +
                        "        <option value=\"LAPTOP\">Laptop</option>\n" +
                        "        <option value=\"SMARTPHONE\">Smartphone</option>\n" +
                        "        <option value=\"TV\">TV</option>\n" +
                        "    </select>\n" +
                        "\n" +
                        "    <div class=\"row\">\n" +
                        "        <div class=\"col\">\n" +
                        "            <input type=\"text\" class=\"form-control\" name=\"product_name\" placeholder=\"Product name\">\n" +
                        "        </div>\n" +
                        "        <div class=\"col\">\n" +
                        "            <input type=\"text\" class=\"form-control\" name=\"description\" placeholder=\"Description\">\n" +
                        "        </div>\n" +
                        "        <div class=\"col\">\n" +
                        "            <input type=\"number\" class=\"form-control\" name=\"price\" placeholder=\"Price\">\n" +
                        "        </div>\n" +
                        "        <div class=\"col\">\n" +
                        "            <input type=\"number\" class=\"form-control\" name=\"stock\" placeholder=\"Stock\">\n" +
                        "        </div>\n" +
                        "    </div>"
                        + "    <button>Upload picture</button>"
                        + "</form>"
        );

        post("/", (request, response) -> {

            Path tempFile = Files.createTempFile(uploadDir.toPath(), "", "");
            ProductDAOImpl productDAO = new ProductDAOImpl();
            request.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            Product product = new Product(tempFile.getFileName(),ProductType.valueOf(request.queryParams("product_type")), request.queryParams("product_name"),
                    request.queryParams("description"), Integer.parseInt(request.queryParams("price")),
                    Integer.parseInt(request.queryParams("stock")));


            Files.copy(request.raw().getPart("img").getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

                ProductDTO productDTO = new ProductDTO(product);
                productDAO.addProduct(productDTO);




            return "<h1>You uploaded this image:<h1><img src='" + tempFile.getFileName() + "'>";


        });
        System.out.println("\nServer started: http://localhost:4567/");

    }

    // methods used for logging
    private static void logInfo(Request req, Path tempFile) throws IOException, ServletException {
        System.out.println("Uploaded file '" + getFileName(req.raw().getPart("img")) + "' saved as '" + tempFile.toAbsolutePath() + "'");
    }

    private static String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
