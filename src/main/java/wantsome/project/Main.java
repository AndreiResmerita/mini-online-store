package wantsome.project;

import spark.Spark;
import wantsome.project.web.Paths;

import java.io.File;

import static spark.Spark.*;
import static spark.debug.DebugScreen.*;
import static wantsome.project.Controller.AboutController.getAboutPage;
import static wantsome.project.Controller.AdminController.*;
import static wantsome.project.Controller.LoginController.*;
import static wantsome.project.Controller.MainController.getMainPage;
import static wantsome.project.Controller.ProductsController.*;
import static wantsome.project.Controller.RegisterController.getRegisterPage;
import static wantsome.project.Controller.RegisterController.handleRegisterPost;

public class Main {

    public static void main(String[] args) {
        enableDebugScreen();
        setup();
        configureRoutesAndStart();
    }

    private static void setup() {

    }

    private static void configureRoutesAndStart() {

        staticFileLocation("public");
        File uploadDir = new File("upload");
        uploadDir.mkdir(); // create the upload directory if it doesn't exist
        Spark.externalStaticFileLocation("upload");
        //configure all routes
        get(Paths.Web.MAIN, getMainPage);
        get(Paths.Web.REGISTER, getRegisterPage);
        get(Paths.Web.LOGIN, getLoginPage);
        get(Paths.Web.PRODUCTS, getProductsPage);
        get(Paths.Web.ADMINPANEL,getLoginPageAdmin);
        get(Paths.Web.DLTPRODADMIN,delete);
        get(Paths.Web.EDTPRODADMIN2,getEditProductAdmin);
        get(Paths.Web.PRODUCTPAGE,getEachProductsPage);
        get(Paths.Web.ABOUTPAGE,getAboutPage);



        post(Paths.Web.REGISTER, handleRegisterPost);
        post(Paths.Web.LOGIN, handleLoginPost);
        post(Paths.Web.LOGOUT, handleLogoutPost);
        post(Paths.Web.ADMINPANEL,handleProductPost);
        post(Paths.Web.EDTPRODADMIN2,getEditProductAdminPost);




        awaitInitialization();
        System.out.println("\nServer started: http://localhost:4567/main");



    }


}
