package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.PatientCardDao;
import com.epam.hospital.dao.UserDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.PatientCardDaoImpl;
import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.SignUpService;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.PatientCardValidatorImpl;
import com.epam.hospital.service.validator.impl.UserValidatorImpl;

public class SignUpServiceImpl implements SignUpService {
    private static final SignUpService instance = new SignUpServiceImpl();
    private static final Validator<User> userValidator = new UserValidatorImpl();
    private static final Validator<PatientCard> patientCardValidator = new PatientCardValidatorImpl();


    public static SignUpService getInstance() {
        return instance;
    }

    @Override
    public boolean signUp(User user, PatientCard patientCard) throws ServiceException {
        if (!userValidator.isValid(user) || !patientCardValidator.isValid(patientCard)) {
            return false;
        }
        UserDao userDao = new UserDaoImpl();
        PatientCardDao patientCardDao = new PatientCardDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(false, userDao, patientCardDao);
            int userId = userDao.saveAndGetId(user);
            patientCard.setUserId(userId);
            patientCardDao.save(patientCard);
            transaction.commit();
            return true;
        } catch (DaoException e) {
            throw new ServiceException("Can't get user by login.", e);
        }
    }
}
