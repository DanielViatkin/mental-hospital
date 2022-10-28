package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.TreatmentCourse;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TreatmentCourseBuilder implements EntityBuilder<TreatmentCourse> {
    public TreatmentCourse build(ResultSet resultSet) throws SQLException {
        TreatmentCourse treatmentCourse = new TreatmentCourse();
        treatmentCourse.setTreatmentCourseId(resultSet.getInt(Column.TREATMENT_COURSE_ID));
        treatmentCourse.setInstruction(resultSet.getString(Column.TREATMENT_COURSE_INSTRUCTION));
        return treatmentCourse;
    }
}
