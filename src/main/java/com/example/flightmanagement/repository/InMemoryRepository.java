package com.example.flightmanagement.repository;

import java.util.*;

public class InMemoryRepository<ID, T> implements IRepository<ID, T> {

    protected Map<ID, T> entities = new HashMap<>();

    @Override
    public void save(T entity) {
        try {
            // presupunem ca entitatea are metoda getId()
            @SuppressWarnings("unchecked")
            ID id = (ID) entity.getClass().getMethod("getId").invoke(entity);
            entities.put(id, entity);
        } catch (Exception e) {
            throw new RuntimeException("Entity must have a getId() method returning the ID", e);
        }
    }

    @Override
    public List<T> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public T findById(ID id) {
        return entities.get(id);
    }

    @Override
    public void delete(ID id) {
        entities.remove(id);
    }
}
