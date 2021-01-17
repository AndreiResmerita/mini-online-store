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

    public static Route addToCart = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        if (getQueryRedirectBack(request) != null) {
            model.put("added", true);
            addInCart(ProductDAO.getById(Integer.parseInt(getParamsProdId(request))), Integer.parseInt(request.queryParams("noOfItems")));
            ProductDTO productDTO = ProductDAO.getById(Integer.parseInt(getParamsProdId(request)));
            model.put("product", productDTO);
            return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
        }
        addInCart(ProductDAO.getById(Integer.parseInt(getParamsProdId(request))), Integer.parseInt(request.queryParams("noOfItems")));
        ProductDTO productDTO = ProductDAO.getById(Integer.parseInt(getParamsProdId(request)));
        model.put("product", productDTO);
        return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
    };

    public static Route getCartPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        DecimalFormat df = new DecimalFormat(
                "#,##0.00",
                new DecimalFormatSymbols(new Locale("de", "DE")));
        BigDecimal value = new BigDecimal(getTotalPrice());
        model.put("price", df.format(value.floatValue()));
        model.put("cs", productDTOList.size());
        model.put("allProducts", productDTOList.stream().distinct().collect(Collectors.toList()));
        model.put("quantity", frequency(productDTOList));
        model.put("listP", productDTOList.size());
        return SparkUtil.render(request, model, Paths.Template.CARTPAGE);
    };

    public static Route finishOrder = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        model.put("quantity", frequency(productDTOList));
        model.put("listP", products);
        model.put("allProducts", productDTOList);
        model.put("price", getTotalPrice());
        model.put("cs", productDTOList.size());
        model.put("orderFinish", true);
        UserDTO userDTO = UserDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        Cart cart = new Cart(PaymentType.valueOf(request.queryParams("type_of_payment")), getTotalPrice());
        CartDTO cartDTO = new CartDTO(cart);
        CartDAO.sendOrder(userDTO, cartDTO);
        for (Map.Entry<ProductDTO, Long> p : frequency(productDTOList).entrySet()) {
            ProductDAO.updateStock(p.getKey().getStock() - p.getValue(), p.getKey().getId());
            OrderItemDAO.insert(cartDTO, p.getKey(), p.getValue(), orderItemDTO);
        }
        productDTOList.clear();
        products.clear();
        return SparkUtil.render(request, model, Paths.Template.CARTPAGE);
    };

    public static Route removeItem = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        ProductDTO productDTO = ProductDAO.getById(Integer.valueOf(request.params("id")));
        remove(productDTO, productDTOList);
        response.redirect(Paths.Web.CARTPAGE);
        return null;
    };
}