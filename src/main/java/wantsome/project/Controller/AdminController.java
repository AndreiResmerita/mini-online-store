package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.CartDAO;
import wantsome.project.DAO.CartDAOImpl;
import wantsome.project.DAO.ProductDAO;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.ToJson;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;

import java.util.HashMap;
import java.util.Map;

import static wantsome.project.DAO.CartDAO.productDTOList;
import static wantsome.project.web.RequestUtil.RequestUtil.getParamsProdId;
import static wantsome.project.web.RequestUtil.RequestUtil.removeSessionAttrLoggedOut;


public class AdminController {

    private static ProductDAO productDAO = new ProductDAOImpl();
    private static CartDAO cartDAO = new CartDAOImpl();

    public static Route getLoginPageAdmin = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("products", productDAO.getAll());
        model.put("quantity", cartDAO.frequency(productDTOList));
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("mostBought", productDAO.getMostBought());
        model.put("data", ToJson.toJson(productDAO.getAllForJSON()));
        return SparkUtil.render(request, model, Paths.Template.ADMINPANEL);
    };

    public static Route delete = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        String query = getParamsProdId(request);
        productDAO.delete(Integer.parseInt(query));
        response.redirect(Paths.Web.ADMINPANEL);
        return null;
    };

    public static Route getEditProductAdmin = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("products", productDAO.getAll());
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        model.put("mostBought", productDAO.getMostBought());
        return SparkUtil.render(request, model, Paths.Template.EDITADMNPNL);
    };

    public static Route getEditProductAdminPost = (Request request, Response response) -> {
        LoginController.ensureUserIsAdmin(request, response);
        ProductDTO product = productDAO.get(Integer.parseInt(getParamsProdId(request)));
        product = new ProductDTO(product.getId(), product.getImg(), product.getProductType(), request.queryParams("product_name"), request.queryParams("description"), Integer.parseInt(request.queryParams("price")), Integer.parseInt(request.queryParams("stock")));
        productDAO.update(product);
        response.redirect(Paths.Web.ADMINPANEL);
        return null;
    };

}
