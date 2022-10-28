package com.epam.hospital.service.logic;

import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.service.exception.ServiceException;

public interface PatientCardService {
    PatientCard getPatientCardById(int id) throws ServiceException;

    int getPatientCardIdByUserId(int userId) throws ServiceException;

    void update(PatientCard patientCard) throws ServiceException;
}
