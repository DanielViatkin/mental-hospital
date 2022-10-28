package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.Drug;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DrugBuilder implements EntityBuilder<Drug> {
    @Override
    public Drug build(ResultSet resultSet) throws SQLException {
        Drug drug = new Drug();
        drug.setDrugId(resultSet.getInt(Column.DRUGS_ID));
        drug.setName(resultSet.getString(Column.DRUGS_NAME));
        return drug;
    }
}
