package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.AbstractDao;
import com.epam.hospital.dao.DrugDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.DrugDaoImpl;
import com.epam.hospital.model.treatment.Drug;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.DrugService;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.DrugValidatorImpl;

import java.util.Optional;

public class DrugServiceImpl implements DrugService {
    private static final DrugService instance = new DrugServiceImpl();
    private static final Validator<Drug> drugValidator = new DrugValidatorImpl();

    public static DrugService getInstance() {
        return instance;
    }

    @Override
    public Drug getDrugById(int id) throws ServiceException {
        AbstractDao<Drug> drugDao = new DrugDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(drugDao);
            Optional<Drug> drugOptional = drugDao.findById(id);
            if (drugOptional.isEmpty()) {
                throw new ServiceException("Cannot find drug with id=" + id);
            }
            return drugOptional.get();
        } catch (DaoException e) {
            throw new ServiceException("Can't get drug.", e);
        }
    }

    @Override
    public int getDrugIdByName(String name) throws ServiceException {
        DrugDao drugDao = new DrugDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(drugDao);
            return drugDao.getDrugIdByName(name);
        } catch (DaoException e) {
            throw new ServiceException("Can't get drug id.", e);
        }
    }

    @Override
    public void save(Drug drug) throws ServiceException {
        if (!drugValidator.isValid(drug)) {
            throw new ServiceException("Invalid drug data.");
        }
        DrugDao drugDao = new DrugDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(drugDao);
            drugDao.save(drug);
        } catch (DaoException e) {
            throw new ServiceException("Can't save drug.", e);
        }
    }
}
