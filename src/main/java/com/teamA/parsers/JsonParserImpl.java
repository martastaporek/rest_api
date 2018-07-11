package com.teamA.parsers;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.teamA.data.AbstractEntity;

public class JsonParserImpl implements JsonParser {

    private final Gson gson;

    public JsonParserImpl(Gson gson) {
        this.gson = gson;
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
}
