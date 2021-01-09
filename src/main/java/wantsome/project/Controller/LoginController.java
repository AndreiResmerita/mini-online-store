package wantsome.project.Controller;

import org.mindrot.jbcrypt.BCrypt;
import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.UserDAOImpl;
import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.web.Paths;
import wantsome.project.web.SparkUtil;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static wantsome.project.web.RequestUtil.RequestUtil.*;

public class LoginController {
    public static Route getLoginPage = (Request request, Response response) -> {

        Map<String, Object> model = new HashMap<>();
        model.put("cs", CartController.productDTOList.size());

        model.put("loggedOut", removeSessionAttrLoggedOut(request));
        return SparkUtil.render(request, model, Paths.Template.LOGIN);
    };

    public static Route handleLoginPost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();


        if (authenticateAsAdmin(getQueryEmail(request), getQueryPassword(request))) {
            request.session().attribute("admin", getQueryEmail(request));
            response.redirect(Paths.Web.ADMINPANEL);
            return SparkUtil.render(request, model, Paths.Template.ADMINPANEL);
        }

        if (!LoginController.authenticate(getQueryEmail(request), getQueryPassword(request))) {
            model.put("authenticationFailed", true);
            return SparkUtil.render(request, model, Paths.Template.LOGIN);
        } else

            model.put("cs", CartController.productDTOList.size());
        model.put("authenticationSucceeded", true);

        request.session().attribute("currentUser", getQueryEmail(request));
        return SparkUtil.render(request, model, Paths.Template.LOGIN);
    };

    public static Route handleLogoutPost = (Request request, Response response) -> {
        request.session().removeAttribute("admin");
        request.session().removeAttribute("currentUser");
        request.session().attribute("loggedOut", true);
        response.redirect(Paths.Web.LOGIN);
        return null;
    };

    public static boolean checkPassword(String plainTextPassword, String hashPass) {
        boolean verifiedPassword;

        if (null == hashPass || !hashPass.startsWith("$2a$"))
            throw new java.lang.IllegalArgumentException("Invalid hash provided for comparison");

        verifiedPassword = BCrypt.checkpw(plainTextPassword, hashPass);

        return (verifiedPassword);
    }

    public static boolean authenticate(String email, String password) throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl();

        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        UserDTO user = userDAO.getUser(email);
        if (user == null) {
            return false;
        }
        return checkPassword(password, user.getPassword());
    }

    public static boolean authenticateAsAdmin(String email, String password) throws SQLException {
        UserDAOImpl userDAO = new UserDAOImpl();

        if (email.isEmpty() || password.isEmpty()) {
            return false;
        }
        UserDTO user = userDAO.getUser(email);
        if (user == null) {
            return false;
        }
        boolean passVerify = checkPassword(password, user.getPassword());
        return user.getUserType().name().equals("ADMIN") && passVerify;
    }

    public static void ensureUserIsLoggedIn(Request request, Response response) {
        if (request.session().attribute("currentUser") == null) {
            request.session().attribute("loginRedirect", request.pathInfo());
            response.redirect(Paths.Web.LOGIN);
        }
    }

    public static void ensureUserIsAdmin(Request request, Response response) {
        if (request.session().attribute("admin") == null) {
            response.redirect(Paths.Web.LOGIN);
        }
    }
}