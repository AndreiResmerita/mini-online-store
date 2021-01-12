package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;

import java.lang.ref.ReferenceQueue;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    public static Route getMainPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();
        model.put("product", productDAO.getRandomProduct());
        return SparkUtil.render(request, model, Paths.Template.MAIN);
    };
}
