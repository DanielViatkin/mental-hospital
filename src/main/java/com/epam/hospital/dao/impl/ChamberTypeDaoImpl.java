package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.ChamberTypeDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.hospital.type.ChamberType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ChamberTypeDaoImpl extends AbstractDaoImpl<ChamberType> implements ChamberTypeDao {
    private final String SAVE_CHAMBER_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
            Table.CHAMBERS_TYPE_TABLE,
            Column.CHAMBERS_TYPE_TITLE,
            Column.CHAMBERS_TYPE_NUMBER_OF_BEDS,
            Column.CHAMBERS_TYPE_PRICE,
            Column.CHAMBER_TYPE_NUMBER_OF_AVAILABLE_ROOMS
    );

    private final String UPDATE_CHAMBER_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=?",
            Table.CHAMBERS_TYPE_TABLE,
            Column.CHAMBERS_TYPE_TITLE,
            Column.CHAMBERS_TYPE_NUMBER_OF_BEDS,
            Column.CHAMBERS_TYPE_PRICE,
            Column.CHAMBER_TYPE_NUMBER_OF_AVAILABLE_ROOMS,
            Column.CHAMBERS_TYPE_ID
    );

    public ChamberTypeDaoImpl() {
        super(BuilderFactory.getChamberTypeBuilder(), Table.CHAMBERS_TYPE_TABLE, Column.CHAMBERS_TYPE_ID);
    }

    @Override
    public void save(ChamberType entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_CHAMBER_QUERY);) {
            setParams(statement, entity, SAVE_CHAMBER_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save chamber type.", e);
        }
    }

    @Override
    public int saveAndGetId(ChamberType entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_CHAMBER_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_CHAMBER_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save chamber type.", e);
        }
    }

    @Override
    public void update(ChamberType entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_CHAMBER_QUERY);) {
            setParams(statement, entity, UPDATE_CHAMBER_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save chamber type.", e);
        }
    }

    @Override
    public boolean isChamberTypeAvailable(int chamberTypeId) throws DaoException {
        return findByField(Column.CHAMBERS_TYPE_ID, chamberTypeId).get(0).getNumberOfFreeRooms() != 0;
    }

    private void setParams(PreparedStatement statement, ChamberType chamberType, String action) throws SQLException {
        statement.setString(1, chamberType.getName());
        statement.setInt(2, chamberType.getNumberOfBeds());
        statement.setDouble(3, chamberType.getPrice());
        statement.setInt(4, chamberType.getNumberOfFreeRooms());
        if (action.equals(UPDATE_CHAMBER_QUERY)) {
            statement.setInt(5, chamberType.getChamberTypeId());
        }
    }

}
