package wantsome.project;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToJson {

    public static String toJson(Object o) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(o);
    }
}
