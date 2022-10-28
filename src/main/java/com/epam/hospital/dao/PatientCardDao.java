package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.PatientCard;

public interface PatientCardDao extends AbstractDao<PatientCard> {
    int findPatientCardIdByUserId(int userId) throws DaoException;
}
