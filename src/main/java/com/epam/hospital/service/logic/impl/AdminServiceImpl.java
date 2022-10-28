package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.UserDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.AdminService;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.UserValidatorImpl;

import java.util.Optional;

public class AdminServiceImpl implements AdminService {
    private static final AdminService instance = new AdminServiceImpl();
    private static final Validator<User> userValidator = new UserValidatorImpl();

    public static AdminService getInstance() {
        return instance;
    }

    @Override
    public boolean isUserBanned(int userId) throws SecurityException {

        return false;
    }

    @Override
    public void setUserBanStatusById(int userId, boolean banStatus) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(true, userDao);
            Optional<User> userOptional = userDao.findById(userId);
            if (userOptional.isEmpty()) {
                throw new ServiceException("Cannot find user with id=" + userId);
            }
            User user = userOptional.get();
            user.setIsBanned(banStatus);
            userDao.update(user);
        } catch (DaoException e) {
            throw new ServiceException("Can't ban user.", e);
        }
    }

    @Override
    public int saveDoctorAndGetId(User user, DoctorInfo doctorInfo) throws ServiceException {
        if (!userValidator.isValid(user)) {
            throw new ServiceException("Invalid user data.");
        }
        UserDao userDao = new UserDaoImpl();
        int userId;
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(false, userDao);
            userId = userDao.saveAndGetId(user);
            doctorInfo.setDoctorId(userId);
            userDao.saveDoctorInfo(doctorInfo);
            transaction.commit();

            return userId;
        } catch (DaoException e) {
            throw new ServiceException("Can't save user/doctor info.", e);
        }
    }
}
