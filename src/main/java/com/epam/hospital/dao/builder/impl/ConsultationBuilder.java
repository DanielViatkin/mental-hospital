package com.epam.hospital.dao.builder.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.builder.EntityBuilder;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.treatment.type.ConsultationStatus;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultationBuilder implements EntityBuilder<Consultation> {

    public Consultation build(ResultSet resultSet) throws SQLException {
        Consultation consultation = new Consultation();
        consultation.setConsultationId(resultSet.getInt(Column.CONSULTATION_ID));
        consultation.setCommunicationType(CommunicationType.valueOf(resultSet.getString(Column.CONSULTATION_COMMUNICATION_TYPE)));
        consultation.setStatus(ConsultationStatus.valueOf(resultSet.getString(Column.CONSULTATION_STATUS)));
        consultation.setDate(resultSet.getTimestamp(Column.CONSULTATION_DATE));
        consultation.setDuration(resultSet.getInt(Column.CONSULTATION_DURATION));
        consultation.setDoctorId(resultSet.getInt(Column.CONSULTATION_DOCTOR_ID));
        consultation.setPatientId(resultSet.getInt(Column.CONSULTATION_PATIENT_ID));
        consultation.setPrice(resultSet.getDouble(Column.CONSULTATION_PRICE));
        consultation.setTreatmentCourseId(resultSet.getInt(Column.CONSULTATION_TREATMENT_COURSE_ID));
        Object childConsultationIdObject = resultSet.getObject(Column.CONSULTATION_CHILD_ID);
        consultation.setChildConsultationId(childConsultationIdObject == null ? null : (Integer) childConsultationIdObject);
        Object parentConsultationIdObject = resultSet.getObject(Column.CONSULTATION_PARENT_ID);
        consultation.setParentConsultationId(parentConsultationIdObject == null ? null : (Integer) parentConsultationIdObject);

        return consultation;
    }
}
