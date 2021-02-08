package wantsome.project;

import spark.Spark;
import wantsome.project.DAO.*;
import wantsome.project.web.Paths;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static wantsome.project.Controller.AboutContactController.getAboutPage;
import static wantsome.project.Controller.AboutContactController.getContatctPage;
import static wantsome.project.Controller.AdminController.*;
import static wantsome.project.Controller.CartController.*;
import static wantsome.project.Controller.LoginController.*;

import static wantsome.project.Controller.MainController.getMainPage;
import static wantsome.project.Controller.ProductsController.*;
import static wantsome.project.Controller.RegisterController.getRegisterPage;
import static wantsome.project.Controller.RegisterController.handleRegisterPost;
import static wantsome.project.Controller.UserController.*;
import static wantsome.project.DBManager.*;

public class Main {

    public static void main(String[] args) {
        enableDebugScreen();
        setup();
        configureRoutesAndStart();
    }

    public static void setup() {
        //methods for creating/populating tables on the first start of application
        createTableIfNotExistUsers();
        createTableIfNotExistProducts();
        createTableIfNotExistCarts();
        createTableIfNotExistOrderItem();
        UserDAO.insertIfNotExistsAdmin();
        UserDAO.insertIfNotExistsCustomer();
        ProductDAO.insertIfNotExistsProduct1();
        ProductDAO.insertIfNotExistsProduct2();
        ProductDAO.insertIfNotExistsProduct3();
        ProductDAO.insertIfNotExistsProduct4();
        ProductDAO.insertIfNotExistsProduct5();
        CartDAO.insertIfNotExistsCart1();
        CartDAO.insertIfNotExistsCart2();
        CartDAO.insertIfNotExistsCart3();
        OrderItemDAO.insertIfNotExistsOrderItem1();
        OrderItemDAO.insertIfNotExistsOrderItem2();
        OrderItemDAO.insertIfNotExistsOrderItem3();
        OrderItemDAO.insertIfNotExistsOrderItem4();
    }

    private static void configureRoutesAndStart() {

        Spark.staticFileLocation("/public");
        staticFileLocation("/public");
        Spark.externalStaticFileLocation("upload");

        //configure all routes
        get(Paths.Web.MAIN, getMainPage);
        get(Paths.Web.REGISTER, getRegisterPage);
        get(Paths.Web.LOGIN, getLoginPage);
        get(Paths.Web.PRODUCTS, getProductsPage);
        get(Paths.Web.ADMINPANEL, getLoginPageAdmin);
        get(Paths.Web.DLTPRODADMIN, delete);
        get(Paths.Web.EDTPRODADMIN, getEditProductAdmin);
        get(Paths.Web.PRODUCTPAGE, getEachProductsPage);
        get(Paths.Web.ABOUTPAGE, getAboutPage);
        get(Paths.Web.CARTPAGE, getCartPage);
        get(Paths.Web.ACCSETTINGS, getAccSet);
        get(Paths.Web.CARTPAGEREMOVEITEM, removeItem);
        get(Paths.Web.LOGORREG, LogOrReg);
        get(Paths.Web.USERORDERS, getOrdersPage);
        get(Paths.Web.ORDERS, getOrderProductsList);
        get(Paths.Web.CONTACT,getContatctPage);

        post(Paths.Web.CARTPAGE, finishOrder);
        post(Paths.Web.REGISTER, handleRegisterPost);
        post(Paths.Web.LOGIN, handleLoginPost);
        post(Paths.Web.LOGOUT, handleLogoutPost);
        post(Paths.Web.ADMINPANEL, handleProductPost);
        post(Paths.Web.EDTPRODADMIN, getEditProductAdminPost);
        post(Paths.Web.PRODUCTPAGE, addToCart);
        post(Paths.Web.ACCSETTINGS, changeAccSet);

        awaitInitialization();
        System.out.println("\nServer started: http://localhost:4567/main");
    }
}
