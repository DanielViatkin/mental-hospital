package com.epam.hospital.service.logic;

import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.service.exception.ServiceException;

import java.util.List;

public interface ConsultationService {
    Consultation getConsultationById(int id) throws ServiceException;

    int saveAndGetId(Consultation consultation) throws ServiceException;

    void update(Consultation consultation) throws ServiceException;

    List<Consultation> getAllConsultationsByPatientCardId(int patientCardId) throws ServiceException;

    List<Consultation> getAllConsultationsByDoctorId(int doctorId) throws ServiceException;

    List<Consultation> getAllRelatedConsultations(int consultationId) throws ServiceException;
}
