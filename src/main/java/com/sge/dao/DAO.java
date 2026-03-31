package com.sge.dao;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {

    protected Connection connection;

    public DAO(Connection connection) {
        this.connection = connection;
    }

    public abstract List<T> findAll();
    public abstract boolean create(T obj);
    public abstract boolean update(T obj);
    public abstract boolean delete(T obj);
    public abstract List<T> find(String rechercher);
    public abstract T find(int id);
}