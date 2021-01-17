package wantsome.project;

import spark.Spark;
import wantsome.project.DTO.UserDTO;
import wantsome.project.Model.User;
import wantsome.project.Model.UserType;
import wantsome.project.web.Paths;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static wantsome.project.Controller.AboutController.getAboutPage;
import static wantsome.project.Controller.AdminController.*;
import static wantsome.project.Controller.CartController.*;
import static wantsome.project.Controller.LoginController.*;

import static wantsome.project.Controller.MainController.getMainPage;
import static wantsome.project.Controller.ProductsController.*;
import static wantsome.project.Controller.RegisterController.getRegisterPage;
import static wantsome.project.Controller.RegisterController.handleRegisterPost;
import static wantsome.project.Controller.UserController.*;

public class Main {

    public static void main(String[] args) {
        enableDebugScreen();
        setup();
        configureRoutesAndStart();

    }

    public static void setup() {
        UserDTO userAdmin = new UserDTO(1,"admin@gmail.com","password", UserType.ADMIN,"Andrei","My address","0722443322");
        UserDTO userCustomer = new UserDTO(1,"example@gmail.com","password", UserType.CUSTOMER,"Andrei","My address","0722443322");
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
        get(Paths.Web.EDTPRODADMIN2, getEditProductAdmin);
        get(Paths.Web.PRODUCTPAGE, getEachProductsPage);
        get(Paths.Web.ABOUTPAGE, getAboutPage);
        get(Paths.Web.CARTPAGE, getCartPage);
        get(Paths.Web.ACCSETTINGS, getAccSet);
        get(Paths.Web.CARTPAGED, removeItem);
        get(Paths.Web.LOGORREG, LogOrReg);
        get(Paths.Web.USERORDERS, getOrdersPage);
        get(Paths.Web.ORDERS, getOrderList);

        post(Paths.Web.CARTPAGE, finishOrder);
        post(Paths.Web.REGISTER, handleRegisterPost);
        post(Paths.Web.LOGIN, handleLoginPost);
        post(Paths.Web.LOGOUT, handleLogoutPost);
        post(Paths.Web.ADMINPANEL, handleProductPost);
        post(Paths.Web.EDTPRODADMIN2, getEditProductAdminPost);
        post(Paths.Web.PRODUCTPAGE, addToCart);
        post(Paths.Web.ACCSETTINGS, changeAccSet);

        awaitInitialization();
        System.out.println("\nServer started: http://localhost:4567/main");


    }


}
