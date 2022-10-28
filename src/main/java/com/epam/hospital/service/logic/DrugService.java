package com.epam.hospital.service.logic;

import com.epam.hospital.model.treatment.Drug;
import com.epam.hospital.service.exception.ServiceException;

public interface DrugService {
    Drug getDrugById(int id) throws ServiceException;

    int getDrugIdByName(String name) throws ServiceException;

    void save(Drug drug) throws ServiceException;
}
