package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.PatientCardDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.PatientCardDaoImpl;
import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.PatientCardService;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.PatientCardValidatorImpl;

import java.util.Optional;

public class PatientCardServiceImpl implements PatientCardService {
    private static final PatientCardService instance = new PatientCardServiceImpl();
    private static final Validator<PatientCard> patientCardValidator = new PatientCardValidatorImpl();


    public static PatientCardService getInstance() {
        return instance;
    }

    @Override
    public PatientCard getPatientCardById(int id) throws ServiceException {
        PatientCardDao patientCardDao = new PatientCardDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(patientCardDao);
            Optional<PatientCard> patientCardOptional = patientCardDao.findById(id);
            if (patientCardOptional.isEmpty()) {
                throw new ServiceException("Cannot find patient card with id=" + id);
            }
            return patientCardOptional.get();
        } catch (DaoException e) {
            throw new ServiceException("Can't get patient card.", e);
        }
    }

    @Override
    public int getPatientCardIdByUserId(int userId) throws ServiceException {
        PatientCardDao patientCardDao = new PatientCardDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(patientCardDao);
            return patientCardDao.findPatientCardIdByUserId(userId);
        } catch (DaoException e) {
            throw new ServiceException("Can't get patient card by user id.", e);
        }
    }

    @Override
    public void update(PatientCard patientCard) throws ServiceException {
        if (!patientCardValidator.isValid(patientCard)) {
            throw new ServiceException("Invalid patient card data.");
        }
        PatientCardDao patientCardDao = new PatientCardDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(patientCardDao);
            patientCardDao.update(patientCard);
        } catch (DaoException e) {
            throw new ServiceException("Can't update patient card.", e);
        }
    }


}
