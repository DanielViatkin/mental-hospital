package com.epam.hospital.service.logic;

import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.service.exception.ServiceException;

import java.util.List;

public interface ChamberService {
    boolean isChamberTypeAvailable(int chamberTypeId) throws ServiceException;

    ChamberType getChamberTypeById(int chamberTypeId) throws ServiceException;

    Chamber getAvailableChamber(int chamberTypeId) throws ServiceException;

    Chamber getChamberById(int chamberId) throws ServiceException;

    List<ChamberType> getAllChamberTypes() throws ServiceException;

    ChamberStaying getChamberStayingByHospitalizationId(int hospitalizationId) throws ServiceException;

    void updateChamber(Chamber chamber) throws ServiceException;

    void updateChamberType(ChamberType chamberType) throws ServiceException;

    void updateChamberStaying(ChamberStaying chamberStaying) throws ServiceException;
}
