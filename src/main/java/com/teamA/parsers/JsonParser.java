package com.teamA.parsers;

import com.teamA.customExceptions.JsonFailure;
import com.teamA.data.AbstractEntity;

import java.util.List;

public interface JsonParser {

    <T extends AbstractEntity> String parse(T entity) throws JsonFailure;
    <T extends AbstractEntity> T parse(String jasonEntity, Class<T> entityType) throws JsonFailure;
    <T extends AbstractEntity> String parseList(List<T> entityList) throws JsonFailure;
}
