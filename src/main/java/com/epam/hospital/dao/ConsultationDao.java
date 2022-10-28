package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Consultation;

import java.util.List;

public interface ConsultationDao extends AbstractDao<Consultation> {
    List<Consultation> findByDoctorId(int doctorId) throws DaoException;

    List<Consultation> findByPatientId(int patientId) throws DaoException;
}
