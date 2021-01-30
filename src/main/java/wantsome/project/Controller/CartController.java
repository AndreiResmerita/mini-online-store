package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.*;
import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.OrderItemDTO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.Cart;
import wantsome.project.Model.PaymentType;

import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.stream.Collectors;

import static wantsome.project.DAO.CartDAO.*;
import static wantsome.project.web.RequestUtil.RequestUtil.getParamsProdId;
import static wantsome.project.web.RequestUtil.RequestUtil.getQueryRedirectBack;


public class CartController {

    private static UserDAO userDAO = new UserDAOImpl();
    private static ProductDAO productDAO = new ProductDAOImpl();
    private static CartDAO cartDAO = new CartDAOImpl();
    private static OrderItemDAO orderItemDAO = new OrderItemDAOImpl() {
    };

    public static Route addToCart = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        if (getQueryRedirectBack(request) != null) {
            model.put("added", true);
            cartDAO.addInCart(productDAO.get(Integer.parseInt(getParamsProdId(request))), Integer.parseInt(request.queryParams("noOfItems")));
            ProductDTO productDTO = productDAO.get(Integer.parseInt(getParamsProdId(request)));
            model.put("product", productDTO);
            return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
        }
        cartDAO.addInCart(productDAO.get(Integer.parseInt(getParamsProdId(request))), Integer.parseInt(request.queryParams("noOfItems")));
        ProductDTO productDTO = productDAO.get(Integer.parseInt(getParamsProdId(request)));
        model.put("product", productDTO);
        return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
    };

    public static Route getCartPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        DecimalFormat df = new DecimalFormat(
                "###,###.##",
                new DecimalFormatSymbols(new Locale("de", "DE")));
        BigDecimal value = new BigDecimal(cartDAO.getTotalPrice());
        model.put("price", df.format(value.floatValue()));
        model.put("cs", productDTOList.size());
        model.put("allProducts", productDTOList.stream().distinct().collect(Collectors.toList()));
        model.put("quantity", cartDAO.frequency(productDTOList));
        model.put("listP", productDTOList.size());
        return SparkUtil.render(request, model, Paths.Template.CARTPAGE);
    };

    public static Route finishOrder = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        model.put("quantity", cartDAO.frequency(productDTOList));
        model.put("listP", products);
        model.put("allProducts", productDTOList);
        model.put("price", cartDAO.getTotalPrice());
        model.put("cs", productDTOList.size());
        model.put("orderFinish", true);
        UserDTO userDTO = userDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        Cart cart = new Cart(PaymentType.valueOf(request.queryParams("type_of_payment")), cartDAO.getTotalPrice());
        CartDTO cartDTO = new CartDTO(cart);
        cartDAO.createOrder(userDTO, cartDTO);
        for (Map.Entry<ProductDTO, Long> p : cartDAO.frequency(productDTOList).entrySet()) {
            productDAO.updateStockOnly(p.getKey().getStock() - p.getValue(), p.getKey().getId());
            orderItemDAO.insert(cartDTO, p.getKey(), p.getValue(), orderItemDTO);
        }
        productDTOList.clear();
        products.clear();
        return SparkUtil.render(request, model, Paths.Template.CARTPAGE);
    };

    public static Route removeItem = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        ProductDTO productDTO = productDAO.get(Integer.valueOf(request.params("id")));
        cartDAO.remove(productDTO, productDTOList);
        response.redirect(Paths.Web.CARTPAGE);
        return null;
    };
}