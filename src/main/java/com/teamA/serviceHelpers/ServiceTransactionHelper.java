package com.teamA.serviceHelpers;

import com.teamA.data.AbstractEntity;

public interface ServiceTransactionHelper {

    long getMaxFreeId();
    <T extends AbstractEntity> boolean saveEntity(T entity);
}
