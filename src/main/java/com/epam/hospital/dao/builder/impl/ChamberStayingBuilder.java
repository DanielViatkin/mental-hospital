package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.ChamberStaying;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChamberStayingBuilder implements EntityBuilder<ChamberStaying> {
    @Override
    public ChamberStaying build(ResultSet resultSet) throws SQLException {
        ChamberStaying chamberStaying = new ChamberStaying();
        chamberStaying.setChamberId(resultSet.getInt(Column.CHAMBER_STAYING_CHAMBER_ID));
        chamberStaying.setHospitalizationId((resultSet.getInt(Column.CHAMBER_STAYING_HOSPITALIZATION_ID)));
        chamberStaying.setDateIn(resultSet.getDate(Column.CHAMBER_STAYING_DATE_IN));
        chamberStaying.setDateOut(resultSet.getDate(Column.CHAMBER_STAYING_DATE_OUT));
        chamberStaying.setPrice(resultSet.getDouble(Column.CHAMBER_STAYING_PRICE));
        return chamberStaying;
    }
}
