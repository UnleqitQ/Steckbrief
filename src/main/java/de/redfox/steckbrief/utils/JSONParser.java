package de.redfox.steckbrief.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JSONParser {
    private static Gson g = new Gson();

    public static JsonObject parse(String json) {
        return g.fromJson(json, JsonObject.class);
    }

    public static String toString(JsonObject e) {
        return g.toJson(e);
    }
}
