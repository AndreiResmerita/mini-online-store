package wantsome.project.Controller;

import org.eclipse.jetty.util.log.Log;
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
import wantsome.project.Model.Product;
import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;

import java.util.*;
import java.util.stream.Collectors;

import static wantsome.project.DAO.CartDAOImpl.*;
import static wantsome.project.web.RequestUtil.RequestUtil.getParamsProdId;
import static wantsome.project.web.RequestUtil.RequestUtil.getQueryRedirectBack;


public class CartController {

    public static Route addToCart = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();

        if (getQueryRedirectBack(request) != null) {
            model.put("added", true);
            addInCart(productDAO.getById(Integer.parseInt(getParamsProdId(request))));
            ProductDTO productDTO = productDAO.getById(Integer.parseInt(getParamsProdId(request)));
            model.put("product", productDTO);
            return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
        }

        addInCart(productDAO.getById(Integer.parseInt(getParamsProdId(request))));
        ProductDTO productDTO = productDAO.getById(Integer.parseInt(getParamsProdId(request)));
        model.put("product", productDTO);
        return SparkUtil.render(request, model, Paths.Template.ONEPRODUCT);
    };

    public static Route getCartPage = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        model.put("price", getTotalPrice());
        model.put("cs", productDTOList.size());
        model.put("allProducts", productDTOList.stream().distinct().collect(Collectors.toList()));
        model.put("quantity", frequency(products));
        model.put("listP", productDTOList.size());
        return SparkUtil.render(request, model, Paths.Template.CARTPAGE);
    };

    public static Route finishOrder = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        OrderItemDTO orderItemDTO = new OrderItemDTO();
        OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
        ProductDAOImpl productDAO = new ProductDAOImpl();
        UserDAOImpl userDAO = new UserDAOImpl();
        CartDAOImpl cartDAO = new CartDAOImpl();

        model.put("quantity", frequency(products));
        model.put("listP", products);
        model.put("allProducts", productDTOList);
        model.put("price", getTotalPrice());
        model.put("cs", productDTOList.size());
        model.put("orderFinish",true);

        UserDTO userDTO = userDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        Cart cart = new Cart(PaymentType.valueOf(request.queryParams("type_of_payment")), getTotalPrice());
        CartDTO cartDTO = new CartDTO(cart);
        cartDAO.sendOrder(userDTO, cartDTO);
        int freq = frequency(products);

        for (ProductDTO p : productDTOList.stream().distinct().collect(Collectors.toList())) {
            productDAO.updateStock(p.getStock() - freq, p);
            orderItemDAO.insert(cartDTO, p, freq, orderItemDTO);
        }
        productDTOList.clear();
        products.clear();
        return SparkUtil.render(request,model,Paths.Template.CARTPAGE);
    };

}