package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.ProductDAO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.ProductsToJson;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;

import java.util.HashMap;
import java.util.Map;

import static wantsome.project.DAO.CartDAO.frequency;
import static wantsome.project.DAO.CartDAO.productDTOList;
import static wantsome.project.web.RequestUtil.RequestUtil.getParamsProdId;
import static wantsome.project.web.RequestUtil.RequestUtil.removeSessionAttrLoggedOut;


public class AdminController {

    public static Route getLoginPageAdmin = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("products", ProductDAO.getAllProducts());
        model.put("quantity", frequency(productDTOList));
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("mostBought", ProductDAO.getMostBoughtProduct());
        model.put("data", ProductsToJson.toJsonProductList(ProductDAO.getAllProductsJson()));
        return SparkUtil.render(request, model, Paths.Template.ADMINPANEL);
    };

    public static Route delete = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        String query = getParamsProdId(request);
        ProductDAO.deleteProduct(Integer.parseInt(query));
        response.redirect(Paths.Web.ADMINPANEL);
        return null;
    };

    public static Route getEditProductAdmin = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("products", ProductDAO.getAllProducts());
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("mostBought", ProductDAO.getMostBoughtProduct());
        return SparkUtil.render(request, model, Paths.Template.EDITADMNPNL);
    };

    public static Route getEditProductAdminPost = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        ProductDTO product = ProductDAO.getById(Integer.parseInt(getParamsProdId(request)));
        product = new ProductDTO(product.getId(), product.getImg(), product.getProductType(), request.queryParams("product_name"), request.queryParams("description"), Integer.parseInt(request.queryParams("price")), Integer.parseInt(request.queryParams("stock")));
        ProductDAO.update(product);
        response.redirect(Paths.Web.ADMINPANEL);
        return null;
    };

}
