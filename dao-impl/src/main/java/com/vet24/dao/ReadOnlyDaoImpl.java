package com.vet24.dao;


import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class ReadOnlyDaoImpl<K extends Serializable, T> {

    @PersistenceContext
    protected EntityManager manager;

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    protected ReadOnlyDaoImpl() {
        this.type = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[1];
    }

    public T getByKey(K key) {
        return manager.find(type, key);
    }

    public boolean isExistByKey(K key) {
        boolean result = false;

        Field id = getId(type);

        if (id != null) {
            String query = "SELECT CASE WHEN (count(*)>0) then true else false end" +
                    " FROM " + type.getName() + " WHERE " + id.getName() + " = :id";
            result = manager
                    .createQuery(query, Boolean.class)
                    .setParameter("id", key)
                    .getSingleResult();
        }
        return result;
    }

    private Field getId(Class<?> classForId) {
        if (classForId.equals(Object.class)) {
            return null;
        }
        for (Field field : classForId.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field;
            }
        }
        return getId(classForId.getSuperclass());
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return manager
                .createQuery("FROM " + type.getName())
                .getResultList();
    }

    public T getByField(String fieldName, Object fieldValue)
            throws SecurityException, NoSuchFieldException {
        Class<?> clazz = fieldValue.getClass();
        T objectField;
        try {
            objectField = (T) clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            while (true) {
                clazz = clazz.getSuperclass();
                if (clazz.equals(Object.class)) {
                    throw e;
                }
                try {
                    objectField = (T) clazz.getDeclaredField(fieldName);
                    break;
                } catch (NoSuchFieldException e1) {
                    System.out.println("The class doesn't have a field of a specified name");
                }
            }
        }
        return objectField;
    }

}
