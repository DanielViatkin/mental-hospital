package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Hospitalization;

import java.util.List;

public interface HospitalizationDao extends AbstractDao<Hospitalization> {
    List<Hospitalization> findByPatientId(int patientId) throws DaoException;

    List<Hospitalization> findByDoctorId(int doctorId) throws DaoException;
}
