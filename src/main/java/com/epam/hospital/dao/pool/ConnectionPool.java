package com.epam.hospital.dao.pool;

import com.epam.hospital.dao.pool.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {
    private static ConnectionPool instance;
    private static BlockingQueue<PooledConnection> allConnections;
    private static BlockingQueue<PooledConnection> engagedConnections;


    private ConnectionPool() {
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public void init(String url, String user, String password, String driverName, int poolSize) throws ConnectionPoolException {
        try {
            Class.forName(driverName);
            allConnections = new ArrayBlockingQueue<>(poolSize);
            engagedConnections = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                allConnections.add(new PooledConnection(connection, this));
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("SQLException in ConnectionPool", e);
        } catch (ClassNotFoundException e) {
            throw new ConnectionPoolException("Cant find database driver", e);
        }
    }

    public void dispose() throws ConnectionPoolException {
        try {
            for (Connection connection : allConnections) {
                connection.close();
            }
            for (Connection connection : engagedConnections) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new ConnectionPoolException("Error closing the connection.", e);
        } finally {
            allConnections.clear();
            engagedConnections.clear();
        }
    }


    public PooledConnection takeConnection() throws ConnectionPoolException {
        PooledConnection connection = null;
        try {
            connection = allConnections.take();
            engagedConnections.add(connection);
        } catch (InterruptedException e) {
            throw new ConnectionPoolException("Error connecting to the data source.", e);
        }
        return connection;
    }

    public void releaseConnection(PooledConnection connection) throws SQLException {
        if (engagedConnections.remove(connection)) {
            try {
                allConnections.put(connection);
            } catch (InterruptedException e) {
                throw new SQLException("Error releasing connection.", e);
            }
        }
    }
}

