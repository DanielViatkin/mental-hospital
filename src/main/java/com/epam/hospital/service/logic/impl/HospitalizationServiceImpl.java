package com.epam.hospital.service.logic.impl;

import com.epam.hospital.dao.ChamberDao;
import com.epam.hospital.dao.ChamberStayingDao;
import com.epam.hospital.dao.ChamberTypeDao;
import com.epam.hospital.dao.HospitalizationDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.ChamberDaoImpl;
import com.epam.hospital.dao.impl.ChamberStayingDaoImpl;
import com.epam.hospital.dao.impl.ChamberTypeDaoImpl;
import com.epam.hospital.dao.impl.HospitalizationDaoImpl;
import com.epam.hospital.model.hospital.Chamber;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.service.logic.HospitalizationService;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.validator.Validator;
import com.epam.hospital.service.validator.impl.ChamberStayingValidatorImpl;
import com.epam.hospital.service.validator.impl.ChamberTypeValidatorImpl;
import com.epam.hospital.service.validator.impl.ChamberValidatorImpl;
import com.epam.hospital.service.validator.impl.HospitalizationValidatorImpl;

import java.util.List;
import java.util.Optional;

public class HospitalizationServiceImpl implements HospitalizationService {
    private static final HospitalizationService instance = new HospitalizationServiceImpl();
    private static final Validator<Hospitalization> hospitalizationValidator = new HospitalizationValidatorImpl();
    private static final Validator<ChamberStaying> chamberStayingValidator = new ChamberStayingValidatorImpl();
    private static final Validator<ChamberType> chamberTypeValidator = new ChamberTypeValidatorImpl();
    private static final Validator<Chamber> chamberValidator = new ChamberValidatorImpl();

    public static HospitalizationService getInstance(){
        return instance;
    }

    @Override
    public Hospitalization getHospitalizationById(int hospitalizationId) throws ServiceException {
        HospitalizationDao hospitalizationDao = new HospitalizationDaoImpl();
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(true, hospitalizationDao);
            Optional<Hospitalization> hospitalizationOptional = hospitalizationDao.findById(hospitalizationId);
            if (hospitalizationOptional.isEmpty()) {
                throw new ServiceException("Cannot find hospitalization with id=" + hospitalizationId);
            }
            return hospitalizationOptional.get();
        } catch(DaoException e){
            throw new ServiceException("Can't get hospitalization.", e);
        }
    }

    @Override
    public List<Hospitalization> getAllHospitalizationsByPatientCardId(int patientCardId) throws ServiceException {
        HospitalizationDao hospitalizationDao = new HospitalizationDaoImpl();
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(hospitalizationDao);
            return hospitalizationDao.findByPatientId(patientCardId);
        } catch(DaoException e){
            throw new ServiceException("Can't get hospitalizations by patient card id.", e);
        }
    }

    @Override
    public List<Hospitalization> getAllHospitalizationsByDoctorCardId(int doctorId) throws ServiceException {
        HospitalizationDao hospitalizationDao = new HospitalizationDaoImpl();
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(hospitalizationDao);
            return hospitalizationDao.findByDoctorId(doctorId);
        } catch(DaoException e){
            throw new ServiceException("Can't get hospitalizations by patient card id.", e);
        }
    }

    @Override
    public int saveHospitalizationAndGetId(Hospitalization hospitalization, ChamberStaying chamberStaying, Chamber chamber) throws ServiceException {
        if (!hospitalizationValidator.isValid(hospitalization) || !chamberStayingValidator.isValid(chamberStaying) || !chamberValidator.isValid(chamber)){
            throw new ServiceException("Invalid hospitalization,  chamber staying, chamber data.");
        }
        HospitalizationDao hospitalizationDao = new HospitalizationDaoImpl();
        ChamberStayingDao chamberStayingDao = new ChamberStayingDaoImpl();
        ChamberDao chamberDao = new ChamberDaoImpl();
        int hospitalizationId;
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(false, hospitalizationDao, chamberStayingDao, chamberDao);

            hospitalizationId = hospitalizationDao.saveAndGetId(hospitalization);

            chamberStaying.setHospitalizationId(hospitalizationId);
            chamberStayingDao.save(chamberStaying);

            chamberDao.update(chamber);

            transaction.commit();


        } catch(DaoException e){
            throw new ServiceException("Can't save hospitalization, chamber staying.", e);
        }
        return hospitalizationId;
    }

    @Override
    public int saveHospitalizationAndGetId(Hospitalization hospitalization, ChamberStaying chamberStaying, Chamber chamber, ChamberType chamberType) throws ServiceException {
        if (!hospitalizationValidator.isValid(hospitalization) ||
                !chamberStayingValidator.isValid(chamberStaying) ||
                !chamberTypeValidator.isValid(chamberType) ||
                !chamberValidator.isValid(chamber)){
            throw new ServiceException("Invalid hospitalization,  chamber staying data.");
        }
        HospitalizationDao hospitalizationDao = new HospitalizationDaoImpl();
        ChamberStayingDao chamberStayingDao = new ChamberStayingDaoImpl();
        ChamberDao chamberDao = new ChamberDaoImpl();
        ChamberTypeDao chamberTypeDao = new ChamberTypeDaoImpl();
        int hospitalizationId;
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(false, hospitalizationDao, chamberStayingDao, chamberDao, chamberTypeDao);

            hospitalizationId = hospitalizationDao.saveAndGetId(hospitalization);

            chamberStaying.setHospitalizationId(hospitalizationId);
            chamberStayingDao.save(chamberStaying);

            chamberDao.update(chamber);

            chamberTypeDao.update(chamberType);
            transaction.commit();
        } catch(DaoException e){
            throw new ServiceException("Can't save hospitalization, chamber staying.", e);
        }

        return hospitalizationId;
    }

    @Override
    public void update(Hospitalization hospitalization) throws ServiceException {
        if (!hospitalizationValidator.isValid(hospitalization)){
            throw new ServiceException("Invalid hospitalization data.");
        }
        HospitalizationDao hospitalizationDao = new HospitalizationDaoImpl();
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(true, hospitalizationDao);
            hospitalizationDao.update(hospitalization);
        } catch(DaoException e){
            throw new ServiceException("Can't update hospitalization.", e);
        }
    }

    @Override
    public void updateChamberStaying(ChamberStaying chamberStaying) throws ServiceException {
        if (!chamberStayingValidator.isValid(chamberStaying)){
            throw new ServiceException("Invalid chamber staying data.");
        }
        ChamberStayingDao chamberStayingDao = new ChamberStayingDaoImpl();
        try(DaoTransactionProvider transaction = new DaoTransactionProvider()){
            transaction.initTransaction(true, chamberStayingDao);
            chamberStayingDao.update(chamberStaying);
        } catch(DaoException e){
            throw new ServiceException("Can't update chamber staying.", e);
        }
    }
}
