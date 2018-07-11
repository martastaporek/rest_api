package com.teamA.serviceHelpers;

import com.teamA.data.AbstractEntity;

public interface ServiceTransactionHelper {

    long getMaxFreeId();
    <T extends AbstractEntity> boolean saveNewEntity(T entity);
    <T extends AbstractEntity> boolean updateEntity(T entity);
}
