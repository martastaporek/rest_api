package com.teamA.parsers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamA.customExceptions.JsonFailure;
import com.teamA.data.AbstractEntity;

import java.io.IOException;
import java.util.List;

public class JacksonParser implements JsonParser {

    private final ObjectMapper mapper;

    public static JsonParser create() {
        return new JacksonParser();
    }

    private JacksonParser() {
        this.mapper = new ObjectMapper();
    }

    @Override
    public <T extends AbstractEntity> String parse(T entity) throws JsonFailure {
        try {
            return mapper.writeValueAsString(entity);
        } catch (JsonProcessingException e) {
            throw new JsonFailure(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractEntity> T parse(String jasonEntity, Class<T> entityType)
            throws JsonFailure {
        try {
            return mapper.readValue(jasonEntity,entityType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new JsonFailure(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractEntity> String parseList(List<T> entityList) throws JsonFailure {
        try {
            return mapper.writeValueAsString(entityList);
        } catch (JsonProcessingException e) {
            throw new JsonFailure(e.getMessage());
        }
    }

    @Override
    public <T extends AbstractEntity> List<T> parseList(String jsonEntityList) throws JsonFailure {
        try {
            TypeReference<List<T>> listType = new TypeReference<List<T>>(){};
            return mapper.readValue(jsonEntityList, listType);
        } catch (IOException e) {
            throw new JsonFailure(e.getMessage());
        }
    }
}
