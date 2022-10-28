package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.UserDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.UserDaoImpl;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.validator.impl.UserValidatorImpl;
import com.epam.hospital.util.Hasher;

import java.util.List;
import java.util.Optional;

import static com.epam.hospital.controller.command.impl.user.SignUpCommand.salt;

public class UserServiceImpl implements UserService {
    private final static UserService instance = new UserServiceImpl();
    private static final UserValidatorImpl userValidator = new UserValidatorImpl();


    public static UserService getInstance() {
        return instance;
    }

    @Override
    public Optional<User> login(String email, String password) throws ServiceException {
        if (!userValidator.isValidOfInjectionAttack(email) ||
                !userValidator.isValidEmail(email) ||
                !userValidator.isValidOfInjectionAttack(password) ||
                !userValidator.isValidPassword(password)) {
            throw new ServiceException("Invalid email, password data.");
        }
        Hasher hasher = new Hasher();
        String hashPassword = hasher.hashString(password, salt);
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> userOptional = userDao.findByEmailPassword(email, hashPassword);
            return userOptional;
        } catch (DaoException e) {
            throw new ServiceException("Can't find out is user exit.", e);
        }
    }

    @Override
    public User getUserById(int id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> userOptional = userDao.findById(id);
            if (userOptional.isEmpty()) {
                throw new ServiceException("Cannot find user with id=" + id);
            }
            return userOptional.get();
        } catch (DaoException e) {
            throw new ServiceException("Can't get user.", e);
        }
    }

    @Override
    public String getUserRoleById(int id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.findUserRole(id);
        } catch (DaoException e) {
            throw new ServiceException("Can't get user by login.", e);
        }
    }

    @Override
    public boolean isUserExistByLogin(String email) throws ServiceException {
        if (!userValidator.isValidOfInjectionAttack(email)) {
            throw new ServiceException("Invalid email data.");
        }
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.isUserExist(email);
        } catch (DaoException e) {
            throw new ServiceException("Can't get user by login.", e);
        }
    }

    @Override
    public DoctorInfo getDoctorInfoById(int id) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<DoctorInfo> doctorInfoOptional = userDao.findDoctorInfoById(id);
            return doctorInfoOptional.orElseThrow(() -> new ServiceException("No doctor with info id=" + id));
        } catch (DaoException e) {
            throw new ServiceException("Can't get doctor info by doctor id.", e);
        }
    }

    @Override
    public User getUserByFullName(String firstName, String lastName) throws ServiceException {
        if (!userValidator.isValidOfInjectionAttack(firstName) || !userValidator.isValidOfInjectionAttack(lastName)) {
            throw new ServiceException("Invalid first, last name data.");
        }
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            Optional<User> userOptional = userDao.findByFullName(firstName, lastName);
            return userOptional.orElseThrow(() -> new ServiceException("No user with full name=" + firstName + " " + lastName));
        } catch (DaoException e) {
            throw new ServiceException("Can't get doctor info by doctor id.", e);
        }
    }

    @Override
    public List<User> getAllDoctors(int doctorRoleId) throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.findAllDoctors(doctorRoleId);
        } catch (DaoException e) {
            throw new ServiceException("Can't get doctor info by doctor id.", e);
        }
    }

    @Override
    public List<User> getAll() throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.findAll();
        } catch (DaoException e) {
            throw new ServiceException("Can't get doctor info by doctor id.", e);
        }
    }

    @Override
    public Integer getUserRoleId() throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.getUserRoleId();
        } catch (DaoException e) {
            throw new ServiceException("Can't get user role id.", e);
        }
    }

    @Override
    public Integer getDoctorRoleId() throws ServiceException {
        UserDao userDao = new UserDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(userDao);
            return userDao.getDoctorRoleId();
        } catch (DaoException e) {
            throw new ServiceException("Can't get doctor role id.", e);
        }
    }
}
