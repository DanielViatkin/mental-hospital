package com.epam.hospital.dao;

import com.epam.hospital.dao.pool.PooledConnection;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.Entity;

import java.util.List;
import java.util.Optional;

public interface AbstractDao<T extends Entity> {
    void save(T entity) throws DaoException;

    int saveAndGetId(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void deleteById(int id) throws DaoException;

    Optional<T> findById(int... ids) throws DaoException;

    List<T> findByField(String fieldName, Object value) throws DaoException;

    List<T> findAll() throws DaoException;

    void setConnection(PooledConnection pooledConnection);
}
