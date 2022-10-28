package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.DiseaseDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.DiseaseDaoImpl;
import com.epam.hospital.model.treatment.Disease;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.DiseaseService;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.DiseaseValidatorImpl;

import java.util.List;
import java.util.Optional;

public class DiseaseServiceImpl implements DiseaseService {
    private static final DiseaseService instance = new DiseaseServiceImpl();
    private static final Validator<Disease> diseaseValidator = new DiseaseValidatorImpl();

    public static DiseaseService getInstance() {
        return instance;
    }

    @Override
    public Disease getDiseaseById(int id) throws ServiceException {
        DiseaseDao diseaseDao = new DiseaseDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(diseaseDao);
            Optional<Disease> diseaseOptional = diseaseDao.findById(id);
            if (diseaseOptional.isEmpty()) {
                throw new ServiceException("Cannot find disease with id=" + id);
            }
            return diseaseOptional.get();
        } catch (DaoException e) {
            throw new ServiceException("Can't get disease.", e);
        }
    }

    @Override
    public int getDiseaseIdByName(String name) throws ServiceException {
        DiseaseDao diseaseDao = new DiseaseDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(diseaseDao);
            return diseaseDao.findIdByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Can't get disease id.", e);
        }
    }

    @Override
    public List<Disease> getAll() throws ServiceException {
        DiseaseDao diseaseDao = new DiseaseDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(diseaseDao);
            return diseaseDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't get diseases.", e);
        }
    }

    @Override
    public int saveAndGetId(Disease disease) throws ServiceException {
        if (!diseaseValidator.isValid(disease)) {
            throw new ServiceException("Invalid disease data.");
        }
        DiseaseDao diseaseDao = new DiseaseDaoImpl();
        int id;
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(diseaseDao);
            id = diseaseDao.saveAndGetId(disease);
        } catch (DaoException e) {
            throw new ServiceException("Can't save disease.", e);
        }
        return id;
    }
}
