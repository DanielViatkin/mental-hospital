package com.epam.hospital.dao.builder.impl;


import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.hospital.Chamber;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChamberBuilder implements EntityBuilder<Chamber> {
    public Chamber build(ResultSet resultSet) throws SQLException {
        Chamber chamber = new Chamber();
        chamber.setChamberId(resultSet.getInt(Column.CHAMBER_ID));
        chamber.setChamberTypeId(resultSet.getInt(Column.CHAMBER_CHAMBER_TYPE_ID));
        chamber.setHospitalId(resultSet.getInt(Column.CHAMBER_HOSPITAL_ID));
        chamber.setNumberOfFreeBeds(resultSet.getInt(Column.CHAMBER_FREE_BEDS));
        return chamber;
    }
}
