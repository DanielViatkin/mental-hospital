package com.epam.hospital.dao;

import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.model.hospital.Chamber;

import java.util.Optional;

public interface ChamberDao extends AbstractDao<Chamber> {
    Optional<Chamber> findChamberWithFreeBeds(int chamberTypeId) throws DaoException;
}
