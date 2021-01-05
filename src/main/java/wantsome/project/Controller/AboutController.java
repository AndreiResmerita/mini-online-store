package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;

import java.util.HashMap;
import java.util.Map;

import static wantsome.project.web.RequestUtil.RequestUtil.removeSessionAttrLoggedOut;

public class AboutController {


    public static Route getAboutPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        return SparkUtil.render(request, model, Paths.Template.ABOUTPAGE);
    };
}
