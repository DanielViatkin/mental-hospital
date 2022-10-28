package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.AbstractDao;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.dao.pool.PooledConnection;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.Entity;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
public abstract class AbstractDaoImpl<T extends Entity> implements AbstractDao<T> {
    private final EntityBuilder<T> builder;
    protected PooledConnection pooledConnection;
    private final String FIND_ALL_QUERY;
    private final String DELETE_BY_ID_QUERY;
    private final String FIND_BY_FIELD_QUERY;

    public AbstractDaoImpl(EntityBuilder<T> builder, String tableName, String idName) {
        this.builder = builder;
        FIND_ALL_QUERY = "SELECT * FROM " + tableName;
        FIND_BY_FIELD_QUERY = "SELECT * FROM " + tableName + " WHERE %s=?";
        DELETE_BY_ID_QUERY = "DELETE FROM " + tableName + " WHERE " + idName + "=?";
    }

    @Override
    public void deleteById(int id) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(DELETE_BY_ID_QUERY)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            log.error("Can't delete entity by id.", e);
            throw new DaoException("Can't delete entity by id.", e);
        }
    }

    @Override
    public Optional<T> findById(int... ids) throws DaoException {
        List<T> list = findByField(Column.ID, ids[0]);

        return list.isEmpty() ? Optional.empty() : Optional.of(list.get(0));
    }

    @Override
    public List<T> findByField(String fieldName, Object value) throws DaoException {
        List<T> entities = new ArrayList<>();
        String query = String.format(
                FIND_BY_FIELD_QUERY,
                fieldName
        );
        try (PreparedStatement statement = pooledConnection.prepareStatement(query);) {
            statement.setObject(1, value);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                entities.add(builder.build(resultSet));
            }
        } catch (SQLException e) {
            log.error("Can't find entity by id.", e);
            throw new DaoException("Can't find by id.", e);
        }
        return entities;
    }

    @Override
    public List<T> findAll() throws DaoException {
        List<T> result = new ArrayList<>();
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_ALL_QUERY);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                T entity = builder.build(resultSet);
                result.add(entity);
            }
        } catch (SQLException e) {
            log.error("Can't find all entity's.", e);
            throw new DaoException("Can't find all entity's.", e);
        }
        return result;
    }

    @Override
    public void setConnection(PooledConnection pooledConnection) {
        this.pooledConnection = pooledConnection;
    }

}
