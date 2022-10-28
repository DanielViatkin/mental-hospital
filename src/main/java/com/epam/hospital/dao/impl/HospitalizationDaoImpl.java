package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.HospitalizationDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Hospitalization;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class HospitalizationDaoImpl extends AbstractDaoImpl<Hospitalization> implements HospitalizationDao {
    private final static String SAVE_HOSPITALIZATION_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
            Table.HOSPITALIZATION_TABLE,
            Column.HOSPITALIZATION_PATIENT_ID,
            Column.HOSPITALIZATION_DOCTOR_ID,
            Column.HOSPITALIZATION_STATUS
    );

    private final static String UPDATE_HOSPITALIZATION_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=? WHERE %s=?",
            Table.HOSPITALIZATION_TABLE,
            Column.HOSPITALIZATION_PATIENT_ID,
            Column.HOSPITALIZATION_DOCTOR_ID,
            Column.HOSPITALIZATION_STATUS,
            Column.HOSPITALIZATION_ID
    );


    public HospitalizationDaoImpl() {
        super(BuilderFactory.getHospitalizationBuilder(), Table.HOSPITALIZATION_TABLE, Column.HOSPITALIZATION_ID);
    }

    @Override
    public void save(Hospitalization entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_HOSPITALIZATION_QUERY);) {
            setParams(statement, entity, SAVE_HOSPITALIZATION_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save hospitalization.", e);
        }
    }

    @Override
    public int saveAndGetId(Hospitalization entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_HOSPITALIZATION_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_HOSPITALIZATION_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save hospitalization.", e);
        }
    }

    @Override
    public void update(Hospitalization entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_HOSPITALIZATION_QUERY);) {
            setParams(statement, entity, UPDATE_HOSPITALIZATION_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update hospitalization.", e);
        }
    }

    @Override
    public List<Hospitalization> findByPatientId(int patientId) throws DaoException {
        return findByField(Column.HOSPITALIZATION_PATIENT_ID, patientId);
    }

    @Override
    public List<Hospitalization> findByDoctorId(int doctorId) throws DaoException {
        return findByField(Column.HOSPITALIZATION_DOCTOR_ID, doctorId);
    }

    private void setParams(PreparedStatement statement, Hospitalization hospitalization, String action) throws SQLException {
        statement.setInt(1, hospitalization.getPatientId());
        statement.setInt(2, hospitalization.getDoctorId());
        statement.setString(3, hospitalization.getStatus().toString());
        if (action.equals(UPDATE_HOSPITALIZATION_QUERY)) {
            statement.setInt(4, hospitalization.getId());
        }
    }
}
