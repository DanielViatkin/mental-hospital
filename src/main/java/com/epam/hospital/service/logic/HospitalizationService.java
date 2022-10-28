package com.epam.hospital.service.logic;

import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.service.exception.ServiceException;

import java.util.List;

public interface HospitalizationService {
    Hospitalization getHospitalizationById(int hospitalizationId) throws ServiceException;

    List<Hospitalization> getAllHospitalizationsByPatientCardId(int patientCardId) throws ServiceException;

    List<Hospitalization> getAllHospitalizationsByDoctorCardId(int patientCardId) throws ServiceException;

    int saveHospitalizationAndGetId(Hospitalization hospitalization, ChamberStaying chamberStaying, Chamber chamber) throws ServiceException;
    int saveHospitalizationAndGetId(Hospitalization hospitalization, ChamberStaying chamberStaying,Chamber chamber, ChamberType chamberType) throws ServiceException;
    void update(Hospitalization hospitalization) throws ServiceException;

    void updateChamberStaying(ChamberStaying chamberStaying) throws ServiceException;

}
