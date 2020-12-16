package wantsome.project;

import spark.Request;
import spark.Response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;
import static wantsome.project.web.SparkUtil.render;

/**
 * Main class of the application. Using Spark framework.
 */
public class Main {

    public static void main(String[] args) {
        setup();
        configureRoutesAndStart();
    }

    private static void setup() {
        //create and configure all needed resources (like db tables, sample data, etc)
    }

    private static void configureRoutesAndStart() {
        staticFileLocation("public");

        //configure all routes
        get("/main", (req, res) -> getMainWebPage(req, res));

        awaitInitialization();
        System.out.println("\nServer started: http://localhost:4567/main");
    }

    //example of returning a web page
    private static Object getMainWebPage(Request req, Response res) {
        Map<String, Object> model = new HashMap<>();
        model.put("serverTime", new Date().toString());
        return render("main.vm", model);
    }
}
