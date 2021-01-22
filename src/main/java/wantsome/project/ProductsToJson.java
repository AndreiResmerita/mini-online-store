package wantsome.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.sql.SQLException;

public class ProductsToJson {

    public static String toJsonProductList(Object o) throws SQLException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }
}
