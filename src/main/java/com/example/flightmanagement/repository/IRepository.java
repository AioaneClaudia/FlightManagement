package com.example.flightmanagement.repository;

import java.util.List;

public interface IRepository<ID, T> {
    void save(T entity);
    List<T> findAll();
    T findById(ID id);
    void delete(ID id);
}
