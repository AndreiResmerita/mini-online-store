package wantsome.project.Controller;

import spark.Request;
import spark.Response;
import spark.Route;
import wantsome.project.DAO.CartDAOImpl;
import wantsome.project.DAO.OrderItemDAOImpl;
import wantsome.project.DAO.ProductDAOImpl;
import wantsome.project.DAO.UserDAOImpl;
import wantsome.project.DTO.CartDTO;
import wantsome.project.DTO.OrderItemDTO;
import wantsome.project.DTO.ProductDTO;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.Cart;
import wantsome.project.Model.OrderItem;
import wantsome.project.Model.PaymentType;
import wantsome.project.web.Paths;
import wantsome.project.web.RequestUtil.RequestUtil;
import wantsome.project.web.SparkUtil;

import java.util.*;

import static wantsome.project.web.RequestUtil.RequestUtil.getParamsProdId;

public class CartController {


    public static Integer frequency(List<ProductDTO> productDTOS) {
        int result = 0;

        Set<ProductDTO> set = new HashSet<ProductDTO>(productDTOS);
        for (ProductDTO p : set) {
            result = Collections.frequency(productDTOS, p);
        }
return result;
    }

    public static List<ProductDTO> productDTOList = new ArrayList<>();

    public static void addInCart(ProductDTO p) {
        productDTOList.add(p);
    }

    public static Integer getTotalPrice() {
        Integer sum = 0;
        for (ProductDTO p : productDTOList) {
            sum += p.getPrice();

        }
        return sum;
    }

    public static List<ProductDTO> getProductsAll() {
        return productDTOList;
    }

    public static Route addToCart = (Request request, Response response) -> {
        LoginController.ensureUserIsLoggedIn(request, response);
        Map<String, Object> model = new HashMap<>();
        ProductDAOImpl productDAO = new ProductDAOImpl();
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
        model.put("allProducts", getProductsAll());
        return SparkUtil.render(request, model, Paths.Template.CARTPAGE);
    };

    public static Route finishOrder = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        model.put("allProducts", getProductsAll());
        model.put("price", getTotalPrice());
        model.put("cs", productDTOList.size());
        OrderItemDAOImpl orderItemDAO = new OrderItemDAOImpl();
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        UserDAOImpl userDAO = new UserDAOImpl();
        CartDAOImpl cartDAO = new CartDAOImpl();
        UserDTO userDTO = userDAO.getUser(RequestUtil.getSessionCurrentUser(request));
        Cart cart = new Cart(PaymentType.valueOf(request.queryParams("type_of_payment")), getTotalPrice());
        CartDTO cartDTO = new CartDTO(cart);
        cartDAO.sendOrder(userDTO, cartDTO);

        for (ProductDTO p : productDTOList) {
            int freq =  frequency(productDTOList);
            orderItemDAO.insert(cartDTO, p,freq,orderItemDTO);
        }
        productDTOList.clear();
        response.redirect(Paths.Web.CARTPAGE);
        return null;

    };

}