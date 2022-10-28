package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.ConsultationDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Consultation;

import java.sql.*;
import java.util.List;


public class ConsultationDaoImpl extends AbstractDaoImpl<Consultation> implements ConsultationDao {
    private final static String SAVE_CONSULTATION_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            Table.CONSULTATION_TABLE,
            Column.CONSULTATION_COMMUNICATION_TYPE,
            Column.CONSULTATION_TREATMENT_COURSE_ID,
            Column.CONSULTATION_DOCTOR_ID,
            Column.CONSULTATION_PATIENT_ID,
            Column.CONSULTATION_DATE,
            Column.CONSULTATION_DURATION,
            Column.CONSULTATION_PRICE,
            Column.CONSULTATION_STATUS,
            Column.CONSULTATION_PARENT_ID,
            Column.CONSULTATION_CHILD_ID
    );
    private final static String UPDATE_CONSULTATION_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=?, %s=? WHERE %s=?",
            Table.CONSULTATION_TABLE,
            Column.CONSULTATION_COMMUNICATION_TYPE,
            Column.CONSULTATION_TREATMENT_COURSE_ID,
            Column.CONSULTATION_DOCTOR_ID,
            Column.CONSULTATION_PATIENT_ID,
            Column.CONSULTATION_DATE,
            Column.CONSULTATION_DURATION,
            Column.CONSULTATION_PRICE,
            Column.CONSULTATION_STATUS,
            Column.CONSULTATION_PARENT_ID,
            Column.CONSULTATION_CHILD_ID,
            Column.CONSULTATION_ID
    );

    public ConsultationDaoImpl() {
        super(BuilderFactory.getConsultationBuilder(), Table.CONSULTATION_TABLE, Column.CONSULTATION_ID);
    }

    @Override
    public void save(Consultation entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_CONSULTATION_QUERY);) {
            setParams(statement, entity, SAVE_CONSULTATION_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save consultation.", e);
        }
    }

    @Override
    public int saveAndGetId(Consultation entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_CONSULTATION_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_CONSULTATION_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save consultation.", e);
        }
    }

    @Override
    public void update(Consultation entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_CONSULTATION_QUERY);) {
            setParams(statement, entity, UPDATE_CONSULTATION_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update consultation.", e);
        }
    }

    @Override
    public List<Consultation> findByDoctorId(int doctorId) throws DaoException {
        return findByField(Column.CONSULTATION_DOCTOR_ID, doctorId);
    }

    @Override
    public List<Consultation> findByPatientId(int patientId) throws DaoException {
        return findByField(Column.CONSULTATION_PATIENT_ID, patientId);
    }


    private void setParams(PreparedStatement statement, Consultation consultation, String action) throws SQLException {
        statement.setString(1, String.valueOf(consultation.getCommunicationType()));
        statement.setObject(2, consultation.getTreatmentCourseId());
        statement.setInt(3, consultation.getDoctorId());
        statement.setInt(4, consultation.getPatientId());
        statement.setTimestamp(5, new Timestamp(consultation.getDate().getTime()));
        statement.setInt(6, consultation.getDuration());
        statement.setDouble(7, consultation.getPrice());
        statement.setString(8, String.valueOf(consultation.getStatus()));
        statement.setObject(9, consultation.getParentConsultationId());
        statement.setObject(10, consultation.getChildConsultationId());
        if (action.equals(UPDATE_CONSULTATION_QUERY)) {
            statement.setInt(11, consultation.getConsultationId());
        }
    }
}
