package com.teamA.serviceHelpers;

import com.teamA.customExceptions.PersistenceFailure;
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
        } catch (Exception notUsed) {
            return 0;
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

    @Override
    public <T extends AbstractEntity> void removeEntity(String entityId, Class<T> entityType)
            throws PersistenceFailure {

        EntityTransaction transaction = null;

        try {
            long id = Long.parseLong(entityId);
            transaction = entityManager.getTransaction();
            transaction.begin();
            T entity = entityManager.find(entityType, id);
            entityManager.remove(entity);
            transaction.commit();

        } catch (Exception e) {
            e.printStackTrace();
            rollBackTransaction(transaction);
            throw new PersistenceFailure(e.getMessage());
        }
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
