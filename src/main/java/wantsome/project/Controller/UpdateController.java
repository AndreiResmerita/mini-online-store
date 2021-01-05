package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;

import java.util.HashMap;
import java.util.Map;

public class UpdateController {
    public static Route getUpdatePage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        return SparkUtil.render(request, model, Paths.Template.UPDATE);
    };
}
