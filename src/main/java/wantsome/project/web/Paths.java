package wantsome.project.web;

public class Paths {
    public static class Web {

        public static final String MAIN = "/main";
        public static final String LOGIN = "/login";
        public static final String LOGOUT = "/logout";
        public static final String REGISTER = "/register";
        public static final String PRODUCTS = "/products";
        public static final String ADMINPANEL = "/adminpanel";
        public static final String DLTPRODADMIN = "/adminpanel/:id";
        public static final String EDTPRODADMIN2 = "/adminpanel/:id/:id";
        public static final String PRODUCTPAGE = "/products/:id";
        public static final String ABOUTPAGE = "/about";
        public static final String CARTPAGE = "/cart";
        public static final String ACCSETTINGS = "/accset";


    }

    public static class Template {
        public final static String MAIN = "velocity/main/main.vm";
        public final static String LOGIN = "/velocity/login/login.vm";
        public final static String REGISTER = "/velocity/register/register.vm";
        public static final String PRODUCTS = "/velocity/products/products.vm";
        public static final String ADMINPANEL = "/velocity/adminPanel/adminpanel.vm";
        public static final String EDITADMNPNL = "/velocity/adminPanel/edit.vm";
        public static final String ONEPRODUCT = "/velocity/products/oneProduct.vm";
        public static final String ABOUTPAGE = "/velocity/about/about.vm";
        public static final String CARTPAGE = "/velocity/cart/cart.vm";
        public static final String ACCSETTINGS = "/velocity/userAcc/accset.vm";

    }
}
