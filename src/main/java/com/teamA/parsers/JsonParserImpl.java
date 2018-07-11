package com.teamA.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.teamA.data.AbstractEntity;

public class JsonParserImpl implements com.teamA.parsers.JsonParser {

    private final Gson gson;
    private final JsonParser jsonParser;

    public JsonParserImpl(Gson gson, JsonParser jsonParser) {
        this.gson = gson;
        this.jsonParser = jsonParser;
    }

    @Override
    public <T extends AbstractEntity> String parse(T entity) {
        return gson.toJson(entity);
    }

    @Override
    public <T extends AbstractEntity> T parse(JsonObject json, Class<T> entityType) {
        T entity = gson.fromJson(json, entityType);
        return entityType.cast(entity);
    }

    @Override
    public <T extends AbstractEntity> JsonObject parse(String jsonString) {
        Object obj = jsonParser.parse(jsonString);
        return (JsonObject) obj;
    }
}
