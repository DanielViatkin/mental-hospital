package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.PatientCardDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.PatientCard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PatientCardDaoImpl extends AbstractDaoImpl<PatientCard> implements PatientCardDao {
    //private static final Logger LOGGER = LogManager.getLogger();
    //private static final ConnectionPool pool = new ConnectionPool();
    public static final String SAVE_PATIENT_CARD_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s, %s) VALUES (?, ?, ?, ?)",
            Table.PATIENT_CARD_TABLE,
            Column.PATIENT_CARD_USER_ID,
            Column.PATIENT_CARD_SPARE_NUMBER,
            Column.PATIENT_CARD_AGE,
            Column.PATIENT_CARD_SEX
    );

    public static final String UPDATE_PATIENT_CARD_QUERY = String.format(
            "UPDATE %s SET %s=?, %s=?, %s=?, %s=? WHERE %s=?",
            Table.PATIENT_CARD_TABLE,
            Column.PATIENT_CARD_USER_ID,
            Column.PATIENT_CARD_SPARE_NUMBER,
            Column.PATIENT_CARD_AGE,
            Column.PATIENT_CARD_SEX,
            Column.PATIENT_CARD_ID
    );

    public PatientCardDaoImpl() {
        super(BuilderFactory.getPatientCardBuilder(), Table.PATIENT_CARD_TABLE, Column.PATIENT_CARD_ID);
    }

    @Override
    public void save(PatientCard entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_PATIENT_CARD_QUERY);) {
            setParams(statement, entity, SAVE_PATIENT_CARD_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save patient card.", e);
        }
    }

    @Override
    public int saveAndGetId(PatientCard entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_PATIENT_CARD_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_PATIENT_CARD_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save patient card.", e);
        }
    }

    @Override
    public void update(PatientCard entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_PATIENT_CARD_QUERY);) {
            setParams(statement, entity, UPDATE_PATIENT_CARD_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save patient card.", e);
        }
    }

    public void setParams(PreparedStatement statement, PatientCard card, String action) throws SQLException {
        statement.setInt(1, card.getUserId());
        statement.setString(2, card.getSpareNumber());
        statement.setInt(3, card.getAge());
        statement.setString(4, card.getSex());
        if (action.equals(UPDATE_PATIENT_CARD_QUERY)) {
            statement.setInt(5, card.getCardId());
        }
    }

    @Override
    public int findPatientCardIdByUserId(int userId) throws DaoException {
        return findByField(Column.PATIENT_CARD_USER_ID, userId).get(0).getCardId();
    }
}
