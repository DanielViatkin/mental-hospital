package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.ChamberDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.hospital.Chamber;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ChamberDaoImpl extends AbstractDaoImpl<Chamber> implements ChamberDao {
    private static final int MIN_NUMBER_OF_AVAILABLE_BEDS = 1;
    private final static String SAVE_CHAMBER_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
            Table.CHAMBERS_TABLE,
            Column.CHAMBER_CHAMBER_TYPE_ID,
            Column.CHAMBER_HOSPITAL_ID,
            Column.CHAMBER_FREE_BEDS
    );
    private final static String UPDATE_CHAMBER_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=? WHERE %s=?",
            Table.CHAMBERS_TABLE,
            Column.CHAMBER_CHAMBER_TYPE_ID,
            Column.CHAMBER_HOSPITAL_ID,
            Column.CHAMBER_FREE_BEDS,
            Column.CHAMBER_ID
    );
    private final static String FIND_CHAMBER_WITH_FREE_BEDS_QUERY = String.format(
            "SELECT * FROM %s WHERE %s >= ? AND %s = ?",
            Table.CHAMBERS_TABLE,
            Column.CHAMBER_FREE_BEDS,
            Column.CHAMBER_CHAMBER_TYPE_ID
    );

    public ChamberDaoImpl() {
        super(BuilderFactory.getChamberBuilder(), Table.CHAMBERS_TABLE, Column.CHAMBER_ID);
    }

    @Override
    public void save(Chamber entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_CHAMBER_QUERY);) {
            setParams(statement, entity, SAVE_CHAMBER_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save chamber.", e);
        }
    }

    @Override
    public int saveAndGetId(Chamber entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_CHAMBER_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_CHAMBER_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save chamber.", e);
        }
    }

    @Override
    public void update(Chamber entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_CHAMBER_QUERY);) {
            pooledConnection.commit();// delete
            setParams(statement, entity, UPDATE_CHAMBER_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update chamber.", e);
        }
    }

    @Override
    public Optional<Chamber> findChamberWithFreeBeds(int chamberTypeId) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_CHAMBER_WITH_FREE_BEDS_QUERY)) {
            statement.setInt(1, MIN_NUMBER_OF_AVAILABLE_BEDS);
            statement.setInt(2, chamberTypeId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Chamber chamber = BuilderFactory.getChamberBuilder().build(resultSet);
                return Optional.of(chamber);
            }
        } catch (SQLException e) {
            throw new DaoException("Can't find chamber with free beds.", e);
        }
        return Optional.empty();
    }

    private void setParams(PreparedStatement statement, Chamber chamber, String action) throws SQLException {
        statement.setInt(1, chamber.getChamberTypeId());
        statement.setInt(2, chamber.getHospitalId());
        statement.setInt(3, chamber.getNumberOfFreeBeds());
        if (action.equals(UPDATE_CHAMBER_QUERY)) {
            statement.setInt(4, chamber.getChamberId());
        }
    }
}
