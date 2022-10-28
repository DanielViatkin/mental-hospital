package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.DiseaseSymptomDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.DiseaseSymptom;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@Slf4j
public class DiseaseSymptomDaoImpl extends AbstractDaoImpl<DiseaseSymptom> implements DiseaseSymptomDao {
    private final static String SAVE_DISEASE_SYMPTOMS_QUERY = String.format(
            "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)",
            Table.DISEASE_SYMPTOMS_TABLE,
            Column.DISEASE_SYMPTOMS_SYMPTOMS,
            Column.DISEASE_SYMPTOMS_COURSE_ID,
            Column.DISEASE_SYMPTOMS_DISEASE_ID
    );
    private final static String UPDATE_DISEASE_SYMPTOMS_QUERY = String.format(
            "UPDATE %s SET %s=? WHERE %s=? AND %s=?",
            Table.DISEASE_SYMPTOMS_TABLE,
            Column.DISEASE_SYMPTOMS_SYMPTOMS,
            Column.DISEASE_SYMPTOMS_COURSE_ID,
            Column.DISEASE_SYMPTOMS_DISEASE_ID
    );

    private final static String FIND_DISEASE_SYMPTOMS_QUERY = String.format(
            "SELECT * FROM %s WHERE %s=? AND %s=?",
            Table.DISEASE_SYMPTOMS_TABLE,
            Column.DISEASE_SYMPTOMS_COURSE_ID,
            Column.DISEASE_SYMPTOMS_DISEASE_ID
    );

    public DiseaseSymptomDaoImpl() {
        super(BuilderFactory.getDiseaseSymptomBuilder(), Table.DISEASE_SYMPTOMS_TABLE, Column.DISEASE_SYMPTOMS_COURSE_ID);
    }

    @Override
    public void save(DiseaseSymptom entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DISEASE_SYMPTOMS_QUERY);) {
            setParams(statement, entity, SAVE_DISEASE_SYMPTOMS_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save disease symptom.", e);
        }
    }

    @Override
    public int saveAndGetId(DiseaseSymptom entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_DISEASE_SYMPTOMS_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_DISEASE_SYMPTOMS_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save disease symptom.", e);
        }
    }

    @Override
    public void update(DiseaseSymptom entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_DISEASE_SYMPTOMS_QUERY);) {
            setParams(statement, entity, UPDATE_DISEASE_SYMPTOMS_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update disease symptom.", e);
        }
    }

    @Override
    public Optional<DiseaseSymptom> findById(int... ids) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(FIND_DISEASE_SYMPTOMS_QUERY);) {
            statement.setObject(1, ids[0]);
            statement.setObject(2, ids[1]);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                DiseaseSymptom entity = BuilderFactory.getDiseaseSymptomBuilder().build(resultSet);
                return Optional.of(entity);
            }
        } catch (SQLException e) {
            log.error("Can't find entity by id.", e);
            throw new DaoException("Can't disease symptom find by id.", e);
        }
        return Optional.empty();
    }

    private void setParams(PreparedStatement statement, DiseaseSymptom diseaseSymptom, String action) throws SQLException {
        statement.setString(1, diseaseSymptom.getSymptoms());
        statement.setInt(2, diseaseSymptom.getTreatmentCourseId());
        statement.setInt(3, diseaseSymptom.getDiseaseId());
    }
}
