package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.ProductDAO;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;
import java.util.HashMap;
import java.util.Map;

public class MainController {
    public static Route getMainPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("product", ProductDAO.getRandomProduct());
        model.put("mostBought",ProductDAO.getMostBoughtProduct());
        return SparkUtil.render(request, model, Paths.Template.MAIN);
    };
}
