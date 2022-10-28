package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.DrugDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Drug;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DrugDaoImpl extends AbstractDaoImpl<Drug> implements DrugDao {
    private final static String SAVE_DRUG_QUERY = String.format(
            "INSERT INTO %s (%s) VALUES (?)",
            Table.DRUGS_TABLE,
            Column.DRUGS_NAME
    );
    private final static String UPDATE_DRUG_QUERY = String.format(
            "UPDATE %s SET %s=? WHERE %s=?",
            Table.DRUGS_TABLE,
            Column.DRUGS_NAME,
            Column.DRUGS_ID
    );

    public DrugDaoImpl() {
        super(BuilderFactory.getDrugBuilder(), Table.DRUGS_TABLE, Column.DRUGS_ID);
    }

    @Override
    public void save(Drug entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DRUG_QUERY);) {
            setParams(statement, entity, SAVE_DRUG_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save drug.", e);
        }
    }

    @Override
    public int saveAndGetId(Drug entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DRUG_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_DRUG_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save drug.", e);
        }
    }

    @Override
    public void update(Drug entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_DRUG_QUERY);) {
            setParams(statement, entity, UPDATE_DRUG_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update drug.", e);
        }
    }

    @Override
    public int getDrugIdByName(String name) throws DaoException {
        return findByField(Column.DRUGS_NAME, name).get(0).getDrugId();
    }

    private void setParams(PreparedStatement statement, Drug drug, String action) throws SQLException {
        statement.setString(1, drug.getName());
        if (action.equals(UPDATE_DRUG_QUERY)) {
            statement.setInt(2, drug.getDrugId());

        }
    }
}
