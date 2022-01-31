package de.redfox.steckbrief.utils;

import com.google.gson.*;

public class JSONParser {
    private static final Gson g = new GsonBuilder().setPrettyPrinting().create();

    public static JsonObject parse(String json) {
        JsonElement jsonElement = g.fromJson(json, JsonElement.class);
        return jsonElement != null ? jsonElement.getAsJsonObject() : new JsonObject();
    }

    public static String toString(JsonObject e) {
        return g.toJson(e);
    }
}
