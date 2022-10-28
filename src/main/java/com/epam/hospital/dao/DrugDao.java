package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Drug;

public interface DrugDao extends AbstractDao<Drug> {
    int getDrugIdByName(String name) throws DaoException;
}
