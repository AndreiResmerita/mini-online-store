package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;
import java.util.HashMap;
import java.util.Map;

import static wantsome.project.DAO.CartDAOImpl.frequency;
import static wantsome.project.DAO.CartDAOImpl.productDTOList;
import static wantsome.project.web.RequestUtil.RequestUtil.*;


public class AdminController {

    public static Route getLoginPageAdmin = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();
        model.put("products", productDAO.getAllProducts());
        model.put("quantity", frequency(productDTOList));
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        return SparkUtil.render(request, model, Paths.Template.ADMINPANEL);
    };

    public static Route delete = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        ProductDAOImpl productDAO = new ProductDAOImpl();
        String query = getParamsProdId(request);
        productDAO.deleteProduct(Integer.parseInt(query));
        response.redirect(Paths.Web.ADMINPANEL);
        return null;
    };

    public static Route getEditProductAdmin = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();
        model.put("products", productDAO.getAllProducts());
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        return SparkUtil.render(request, model, Paths.Template.EDITADMNPNL);
    };

    public static Route getEditProductAdminPost = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        ProductDAOImpl productDAO = new ProductDAOImpl();
        ProductDTO product = productDAO.getById(Integer.parseInt(getParamsProdId(request)));
        product = new ProductDTO(product.getId(), product.getImg(), product.getProductType(), request.queryParams("product_name"), request.queryParams("description"), Integer.parseInt(request.queryParams("price")), Integer.parseInt(request.queryParams("stock")));
        productDAO.update(product);
        response.redirect(Paths.Web.ADMINPANEL);
        return null;
    };

}
