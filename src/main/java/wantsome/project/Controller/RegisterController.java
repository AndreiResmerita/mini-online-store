package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.UserDAO;
import wantsome.project.DAO.UserDAOImpl;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.User;
import wantsome.project.Model.UserType;
import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;
import java.util.HashMap;
import java.util.Map;

import static wantsome.project.DAO.CartDAOImpl.products;
import static wantsome.project.web.RequestUtil.RequestUtil.*;

public class RegisterController {

    public static Route getRegisterPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        UserDAOImpl userDAO = new UserDAOImpl();
        UserDTO userDTO = userDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        if (userDTO!=null){
            model.put("alreadylogged",true);
            model.put("cs", products.size());
            return SparkUtil.render(request,model,Paths.Template.MAIN);
        }
        return SparkUtil.render(request, model, Paths.Template.REGISTER);
    };

    public static Route handleRegisterPost = (Request request, Response response) -> {
        UserDAO userDAO = new UserDAOImpl();
        Map<String, Object> model = new HashMap<>();
        User user = new User(request.queryParams("email"), request.queryParams("password"), UserType.CUSTOMER,
                request.queryParams("name"), request.queryParams("address"),
                request.queryParams("phone"));
        UserDTO userDTO = new UserDTO(user);
        userDAO.addCustomer(userDTO);
        model.put("registrationSucceeded", true);
        request.session().attribute("currentUser", getQueryEmail(request));

        return SparkUtil.render(request,model,Paths.Template.MAIN);
    };

}
