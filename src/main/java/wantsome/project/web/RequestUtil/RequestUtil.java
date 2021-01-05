package wantsome.project.web.RequestUtil;

import spark.Request;

public class RequestUtil {

    public static String getQueryEmail(Request request) {
        return request.queryParams("email");
    }

    public static String getParamsProdId(Request request) {
        return request.params(":id");
    }

    public static String getQueryPassword(Request request) {
        return request.queryParams("password");
    }

    public static String getSessionCurrentUser(Request request) {
        return request.session().attribute("currentUser");
    }

    public static boolean removeSessionAttrLoggedOut(Request request) {
        Object loggedOut = request.session().attribute("loggedOut");
        request.session().removeAttribute("loggedOut");
        return loggedOut != null;
    }

    public static String getSessionAdmin(Request request) {
        return request.session().attribute("admin");
    }

    public static boolean clientAcceptsHtml(Request request) {
        String accept = request.headers("Accept");
        return accept != null && accept.contains("text/html");
    }


}
