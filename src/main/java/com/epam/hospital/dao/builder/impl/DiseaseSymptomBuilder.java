package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.DiseaseSymptom;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiseaseSymptomBuilder implements EntityBuilder<DiseaseSymptom> {
    @Override
    public DiseaseSymptom build(ResultSet resultSet) throws SQLException {
        DiseaseSymptom diseaseSymptom = new DiseaseSymptom();
        diseaseSymptom.setDiseaseId(resultSet.getInt(Column.DISEASE_SYMPTOMS_DISEASE_ID));
        diseaseSymptom.setTreatmentCourseId(resultSet.getInt(Column.DISEASE_SYMPTOMS_COURSE_ID));
        diseaseSymptom.setSymptoms(resultSet.getString(Column.DISEASE_SYMPTOMS_SYMPTOMS));
        return diseaseSymptom;
    }
}
