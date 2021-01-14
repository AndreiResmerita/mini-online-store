package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;

import javax.xml.parsers.SAXParser;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class LoginOrRegister {
    public static Route LogOrReg = (Request request, Response response) ->{
        Map<String,Object>model = new HashMap<>();
        return SparkUtil.render(request, model, Paths.Template.LOGORREG);

    };
}
