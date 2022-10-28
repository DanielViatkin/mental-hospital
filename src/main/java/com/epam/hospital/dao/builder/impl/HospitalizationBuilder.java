package com.epam.hospital.dao.builder.impl;


import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HospitalizationBuilder implements EntityBuilder<Hospitalization> {
    public Hospitalization build(ResultSet resultSet) throws SQLException {
        Hospitalization hospitalization = Hospitalization.builder()
                .id(resultSet.getInt(Column.HOSPITALIZATION_ID))
                .patientId(resultSet.getInt(Column.HOSPITALIZATION_PATIENT_ID))
                .status(HospitalizationStatus.valueOf(resultSet.getString(Column.HOSPITALIZATION_STATUS)))
                .doctorId(resultSet.getInt(Column.HOSPITALIZATION_DOCTOR_ID))
                .build();
        return hospitalization;
    }
}
