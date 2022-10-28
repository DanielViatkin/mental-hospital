package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.user.info.DoctorInfo;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorInfoBuilder implements EntityBuilder<DoctorInfo> {
    @Override
    public DoctorInfo build(ResultSet resultSet) throws SQLException {
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setDoctorId(resultSet.getInt(Column.DOCTOR_INFO_ID));
        doctorInfo.setClassification(resultSet.getInt(Column.DOCTOR_INFO_CLASSIFICATION));
        doctorInfo.setWorkExperience(resultSet.getInt(Column.DOCTOR_INFO_WORK_EXPERIENCE));
        doctorInfo.setSpecialization(resultSet.getString(Column.DOCTOR_INFO_SPECIALIZATION));
        doctorInfo.setPrice(resultSet.getDouble((Column.DOCTOR_INFO_PRICE)));
        return doctorInfo;
    }
}
