package com.epam.hospital.dao.builder.impl;


import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.hospital.type.ChamberType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ChamberTypeBuilder implements EntityBuilder<ChamberType> {

    public ChamberType build(ResultSet resultSet) throws SQLException {
        ChamberType chamberType = new ChamberType();
        chamberType.setChamberTypeId(resultSet.getInt(Column.CHAMBERS_TYPE_ID));
        chamberType.setName(resultSet.getString(Column.CHAMBERS_TYPE_TITLE));
        chamberType.setPrice(resultSet.getDouble(Column.CHAMBERS_TYPE_PRICE));
        chamberType.setNumberOfBeds(resultSet.getInt(Column.CHAMBERS_TYPE_NUMBER_OF_BEDS));
        chamberType.setNumberOfFreeRooms(resultSet.getInt(Column.CHAMBER_TYPE_NUMBER_OF_AVAILABLE_ROOMS));
        chamberType.setImageRef(resultSet.getString(Column.CHAMBERS_IMAGE_REF));
        return chamberType;
    }
}
