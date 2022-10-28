package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.hospital.type.ChamberType;

public interface ChamberTypeDao extends AbstractDao<ChamberType> {
    boolean isChamberTypeAvailable(int chamberTypeId) throws DaoException;
}
