package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.Disease;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DiseaseBuilder implements EntityBuilder<Disease> {
    @Override
    public Disease build(ResultSet resultSet) throws SQLException {
        Disease disease = new Disease();
        disease.setDiseaseId(resultSet.getInt(Column.DISEASES_ID));
        disease.setDescription(resultSet.getString(Column.DISEASES_DESCRIPTION));
        disease.setName(resultSet.getString(Column.DISEASES_NAME));
        return disease;
    }
}