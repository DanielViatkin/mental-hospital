package com.epam.hospital.dao.provider;

import com.epam.hospital.dao.AbstractDao;
import com.epam.hospital.dao.pool.ConnectionPool;
import com.epam.hospital.dao.pool.PooledConnection;
import com.epam.hospital.dao.pool.exception.ConnectionPoolException;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.Entity;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class DaoTransactionProvider implements AutoCloseable {
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private PooledConnection pooledConnection;

    @SafeVarargs
    public final void initTransaction(boolean isAutoCommit, AbstractDao<? extends Entity>... daos) throws DaoException {
        if (pooledConnection == null) {
            try {
                pooledConnection = connectionPool.takeConnection();
            } catch (ConnectionPoolException e) {
                throw new DaoException(e);
            }
        }

        try {
            pooledConnection.setAutoCommit(isAutoCommit);
        } catch (SQLException e) {
            log.error("Init transaction error: " + e);
            throw new DaoException(e);
        }

        for (AbstractDao<? extends Entity> daoElement : daos) {
            daoElement.setConnection(pooledConnection);
        }
    }


    @SafeVarargs
    public final void initTransaction(AbstractDao<? extends Entity>... daos) throws DaoException {
        initTransaction(true, daos);
    }

    public void commit() throws DaoException {
        try {
            pooledConnection.commit();
        } catch (SQLException e) {
            log.error("Commit error: " + e);
            throw new DaoException(e);
        }
    }

    public void rollback() {
        try {
            pooledConnection.rollback();
        } catch (SQLException e) {
            log.error("Rollback error: " + e);
        }
    }

    @Override
    public void close() {
        try {
            if (!pooledConnection.getAutoCommit()) {
                rollback();
            }
        } catch (SQLException e) {
            log.error("Get auto commit error: " + e);
        }
        try {
            pooledConnection.close();
        } catch (SQLException e) {
            log.error("Proxy connection error: " + e);
        }
    }
}
