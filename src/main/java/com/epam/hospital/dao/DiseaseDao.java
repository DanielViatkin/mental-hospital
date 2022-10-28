package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.treatment.Disease;

public interface DiseaseDao extends AbstractDao<Disease> {
    int findIdByName(String name) throws DaoException;
}
