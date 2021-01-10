package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.UserDAOImpl;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.User;
import wantsome.project.Model.UserType;
import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;

import java.util.HashMap;
import java.util.Map;

public class UserController {

    public static Route getAccSet = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("cs", CartController.productDTOList.size());
        return SparkUtil.render(request,model, Paths.Template.ACCSETTINGS);
    };
    public static Route changeAccSet = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("cs", CartController.productDTOList.size());
        UserDAOImpl userDAO = new UserDAOImpl();
        UserDTO userDTOOLD = userDAO.getUser(RequestUtil.getSessionCurrentUser(request));

        User user = new User(request.queryParams("email"), request.queryParams("password"), UserType.CUSTOMER,
                request.queryParams("name"), request.queryParams("address"),
                request.queryParams("phone_number"));
        UserDTO userDTO = new UserDTO(user);
        userDAO.update(userDTO,userDTOOLD.getId());
        return SparkUtil.render(request,model,Paths.Template.ACCSETTINGS);

    };



}
