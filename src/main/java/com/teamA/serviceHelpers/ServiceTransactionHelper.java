package com.teamA.serviceHelpers;

import com.teamA.customExceptions.PersistenceFailure;
import com.teamA.data.AbstractEntity;

public interface ServiceTransactionHelper {

    long getMaxFreeId();
    <T extends AbstractEntity> boolean saveNewEntity(T entity);
    <T extends AbstractEntity> boolean updateEntity(T entity);
    <T extends AbstractEntity> void removeEntity(String entityId, Class<T> entityType) throws PersistenceFailure;
}
