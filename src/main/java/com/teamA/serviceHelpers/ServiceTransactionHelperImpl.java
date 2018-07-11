package com.teamA.serviceHelpers;

import com.teamA.data.AbstractEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

public class ServiceTransactionHelperImpl implements ServiceTransactionHelper {

    private final EntityManager entityManager;

    public static ServiceTransactionHelper create(EntityManager entityManager) {
        return new ServiceTransactionHelperImpl(entityManager);
    }

    private ServiceTransactionHelperImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public long getMaxFreeId() {
        try {
            return entityManager.createQuery("select max(e.id) from AbstractEntity e", Long.class).getSingleResult();
        } catch (NullPointerException notUsed) {
            return 1;
        }
    }

    @Override
    public <T extends AbstractEntity> boolean saveNewEntity(T entity) {
        return save(entity, true);
    }

    @Override
    public <T extends AbstractEntity> boolean updateEntity(T entity) {
        return save(entity, false);
    }

    private void rollBackTransaction(EntityTransaction transaction) {
        if (transaction != null) {
            try {
                transaction.rollback();
            } catch (Exception notUsed) {}
        }
    }

    private <T extends AbstractEntity> boolean save(T entity, boolean isNewEntity) {
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            if (isNewEntity) {
                entityManager.persist(entity);
            } else {
                entityManager.merge(entity);
            }
            transaction.commit();
        } catch (Exception notUsed) {
            rollBackTransaction(transaction);
            return false;
        }
        return true;
    }
}
