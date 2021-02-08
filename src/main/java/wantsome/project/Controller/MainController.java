package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.ProductDAO;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.web.Paths;
import wantsome.project.web.ViewUtil;

import java.util.HashMap;
import java.util.Map;

public class MainController {

    private static ProductDAO productDAO = new ProductDAOImpl();

    public static Route getMainPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("product", productDAO.getRandomProduct());
        model.put("mostBought", productDAO.getMostBought());
        return ViewUtil.render(request, model, Paths.Template.MAIN);
    };
}
