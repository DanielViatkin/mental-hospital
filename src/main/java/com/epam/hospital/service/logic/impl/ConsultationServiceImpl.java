package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.ConsultationDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.ConsultationDaoImpl;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ConsultationService;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.ConsultationValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsultationServiceImpl implements ConsultationService {
    private static final ConsultationService instance = new ConsultationServiceImpl();
    private static final Validator<Consultation> consultationValidator = new ConsultationValidatorImpl();

    private ConsultationServiceImpl() {
    }

    public static ConsultationService getInstance() {
        return instance;
    }

    @Override
    public Consultation getConsultationById(int id) throws ServiceException {
        ConsultationDao consultationDao = new ConsultationDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(true, consultationDao);
            Optional<Consultation> consultationOptional = consultationDao.findById(id);
            if (consultationOptional.isEmpty()) {
                throw new ServiceException("Cannot find consultation with id=" + id);
            }
            return consultationOptional.get();
        } catch (DaoException e) {
            throw new ServiceException("Can't get consultation.", e);
        }
    }

    @Override
    public int saveAndGetId(Consultation consultation) throws ServiceException {
        if (!consultationValidator.isValid(consultation)) {
            throw new ServiceException("Invalid consultation: " + consultation);
        }
        ConsultationDao consultationDao = new ConsultationDaoImpl();
        int id;
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(true, consultationDao);
            id = consultationDao.saveAndGetId(consultation);
        } catch (DaoException e) {
            throw new ServiceException("Can't save consultation.", e);
        }
        return id;
    }

    @Override
    public void update(Consultation consultation) throws ServiceException {
        if (!consultationValidator.isValid(consultation)) {
            throw new ServiceException("Invalid consultation data.");
        }
        ConsultationDao consultationDao = new ConsultationDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(true, consultationDao);
            consultationDao.update(consultation);
        } catch (DaoException e) {
            throw new ServiceException("Can't update consultation.", e);
        }
    }

    @Override
    public List<Consultation> getAllConsultationsByPatientCardId(int patientCardId) throws ServiceException {
        ConsultationDao consultationDao = new ConsultationDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(true, consultationDao);
            return consultationDao.findByPatientId(patientCardId);
        } catch (DaoException e) {
            throw new ServiceException("Can't get consultations by patient id.", e);
        }
    }

    @Override
    public List<Consultation> getAllConsultationsByDoctorId(int doctorId) throws ServiceException {
        ConsultationDao consultationDao = new ConsultationDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(true, consultationDao);
            return consultationDao.findByDoctorId(doctorId);
        } catch (DaoException e) {
            throw new ServiceException("Can't get consultations by doctor id.", e);
        }
    }

    @Override
    public List<Consultation> getAllRelatedConsultations(int consultationId) throws ServiceException {
        ConsultationDao consultationDao = new ConsultationDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(false, consultationDao);

            int currentHeadConsultationId = consultationId;
            Consultation currentHeadConsultation = null;
            do {
                if (currentHeadConsultation != null) {
                    currentHeadConsultationId = currentHeadConsultation.getParentConsultationId();
                }
                Optional<Consultation> headConsultationOptional = consultationDao.findById(currentHeadConsultationId);
                Consultation newConsultation = headConsultationOptional.orElseThrow(() -> new ServiceException("No consultation with id=" + consultationId));
                currentHeadConsultationId = newConsultation.getConsultationId();
                currentHeadConsultation = newConsultation;
            } while (currentHeadConsultation.getParentConsultationId() != null);

            List<Consultation> consultations = new ArrayList<>();
            consultations.add(currentHeadConsultation);

            Consultation currentConsultation = currentHeadConsultation;
            while (currentConsultation.getChildConsultationId() != null) {
                Optional<Consultation> childConsultationOptional = consultationDao.findById(currentConsultation.getChildConsultationId());
                Consultation childConsultation = childConsultationOptional.orElseThrow(() -> new ServiceException("No consultation with id=" + consultationId));
                consultations.add(childConsultation);
                currentConsultation = childConsultation;
            }

            return consultations;
        } catch (DaoException e) {
            throw new ServiceException("Can't get consultations by doctor id.", e);
        }
    }


}
