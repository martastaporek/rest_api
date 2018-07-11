package com.teamA.parsers;

import com.google.gson.JsonObject;
import com.teamA.data.AbstractEntity;

public interface JsonParser {

    <T extends AbstractEntity> String parse(T entity);
    <T extends AbstractEntity> T parse(JsonObject json, Class<T> entityType);
}
