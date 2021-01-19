package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.CartDAO;
import wantsome.project.DAO.UserDAO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.User;
import wantsome.project.Model.UserType;
import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static wantsome.project.DAO.CartDAO.*;


public class UserController {

    public static Route getAccSet = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("cs", productDTOList.size());
        return SparkUtil.render(request, model, Paths.Template.ACCSETTINGS);
    };
    public static Route changeAccSet = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("cs", productDTOList.size());
        UserDTO userDTOOLD = UserDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        User user = new User(request.queryParams("email"), request.queryParams("password"), UserType.CUSTOMER,
                request.queryParams("name"), request.queryParams("address"),
                request.queryParams("phone_number"));
        UserDTO userDTO = new UserDTO(user);
        UserDAO.update(userDTO, userDTOOLD.getId());
        return SparkUtil.render(request, model, Paths.Template.ACCSETTINGS);
    };

    public static Route getOrdersPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        UserDTO userDTO = UserDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        model.put("carts", CartDAO.getAllOrders(userDTO.getId()));
        return SparkUtil.render(request, model, Paths.Template.USERORDERS);
    };

    public static Route getOrderProductsList = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        DecimalFormat df = new DecimalFormat(
                "###,###.##",
                new DecimalFormatSymbols(new Locale("de", "DE")));
        BigDecimal value = new BigDecimal(getTotalPriceForCart(Integer.parseInt(request.params("id"))));
        CartDAO.getListOrder(CartDAO.getById(Integer.parseInt(request.params("id"))).getId());
        model.put("orders", CartDAO.getListOrder(CartDAO.getById(Integer.parseInt(request.params("id"))).getId()));
        model.put("totalPrice", df.format(value.floatValue()));
        return SparkUtil.render(request, model, Paths.Template.ORDERS);
    };
}