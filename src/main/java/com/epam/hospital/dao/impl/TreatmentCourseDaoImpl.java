package com.epam.hospital.dao.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.constant.database.Table;
import com.epam.hospital.dao.TreatmentCourseDao;
import com.epam.hospital.dao.builder.BuilderFactory;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.TreatmentCourse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TreatmentCourseDaoImpl extends AbstractDaoImpl<TreatmentCourse> implements TreatmentCourseDao {
    public static final String SAVE_TREATMENT_COURSE_QUERY = String.format(
            "INSERT INTO %s (%s) VALUES (?)",
            Table.TREATMENT_COURSES_TABLE,
            Column.TREATMENT_COURSE_INSTRUCTION
    );
    public static final String UPDATE_TREATMENT_COURSE_QUERY = String.format(
            "UPDATE %s SET %s=? WHERE %s=?",
            Table.TREATMENT_COURSES_TABLE,
            Column.TREATMENT_COURSE_INSTRUCTION,
            Column.TREATMENT_COURSE_ID
    );

    public TreatmentCourseDaoImpl() {
        super(BuilderFactory.getTreatmentCourseBuilder(), Table.TREATMENT_COURSES_TABLE, Column.TREATMENT_COURSE_ID);
    }

    @Override
    public void save(TreatmentCourse entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_TREATMENT_COURSE_QUERY);) {
            setParams(statement, entity, SAVE_TREATMENT_COURSE_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't save treatment course.", e);
        }
    }

    @Override
    public int saveAndGetId(TreatmentCourse entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(SAVE_TREATMENT_COURSE_QUERY, Statement.RETURN_GENERATED_KEYS);) {
            setParams(statement, entity, SAVE_TREATMENT_COURSE_QUERY);
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new DaoException("Can't save treatment course.", e);
        }
    }

    @Override
    public void update(TreatmentCourse entity) throws DaoException {
        try (PreparedStatement statement = pooledConnection.prepareStatement(UPDATE_TREATMENT_COURSE_QUERY);) {
            setParams(statement, entity, UPDATE_TREATMENT_COURSE_QUERY);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Can't update treatment course.", e);
        }
    }

    private void setParams(PreparedStatement statement, TreatmentCourse treatmentCourse, String action) throws SQLException {
        statement.setString(1, treatmentCourse.getInstruction());
        if (action.equals(UPDATE_TREATMENT_COURSE_QUERY)) {
            statement.setInt(1, treatmentCourse.getTreatmentCourseId());

        }
    }

}
