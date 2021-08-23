package com.example.haha.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JsonParserTest {
    public static void main(String[] args) {
        String json = "{ \"name\":\"java书籍\", \"authors\":[\"Jerry\",\"Tom\"]}";
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(json);
        System.out.println("jsonElement="+jsonElement);
        System.out.println(jsonElement.getAsJsonObject().get("authors").isJsonArray());
    }
}
