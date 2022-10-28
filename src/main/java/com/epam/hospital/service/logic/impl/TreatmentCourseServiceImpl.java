package com.epam.hospital.service.logic.impl;

import com.epam.hospital.constant.database.Column;
import com.epam.hospital.dao.AbstractDao;
import com.epam.hospital.dao.DiseaseSymptomDao;
import com.epam.hospital.dao.DrugRecipeDao;
import com.epam.hospital.dao.TreatmentCourseDao;
import com.epam.hospital.dao.exception.DaoException;
import com.epam.hospital.dao.provider.DaoTransactionProvider;
import com.epam.hospital.dao.impl.DiseaseSymptomDaoImpl;
import com.epam.hospital.dao.impl.DrugRecipeDaoImpl;
import com.epam.hospital.dao.impl.TreatmentCourseDaoImpl;
import com.epam.hospital.model.treatment.DiseaseSymptom;
import com.epam.hospital.model.treatment.DrugRecipe;
import com.epam.hospital.model.treatment.TreatmentCourse;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.TreatmentCourseService;

import java.util.List;
import java.util.Optional;

public class TreatmentCourseServiceImpl implements TreatmentCourseService {
    private static TreatmentCourseService instance;

    private TreatmentCourseServiceImpl() {

    }

    public static TreatmentCourseService getInstance() {
        if (instance == null) {
            instance = new TreatmentCourseServiceImpl();
        }
        return instance;
    }

    @Override
    public TreatmentCourse getTreatmentCourseById(int treatmentCourseId) throws ServiceException {
        TreatmentCourseDao treatmentCourseDao = new TreatmentCourseDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(treatmentCourseDao);
            Optional<TreatmentCourse> treatmentCourseOptional = treatmentCourseDao.findById(treatmentCourseId);
            if (treatmentCourseOptional.isEmpty()) {
                throw new ServiceException("Cannot find treatment course with id=" + treatmentCourseId);
            }
            return treatmentCourseOptional.get();
        } catch (DaoException e) {
            throw new ServiceException("Can't get treatment course.", e);
        }
    }

    @Override
    public List<DiseaseSymptom> getDiseaseSymptoms(int treatmentCourseId) throws ServiceException {
        AbstractDao<DiseaseSymptom> diseaseSymptomDao = new DiseaseSymptomDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(diseaseSymptomDao);
            return diseaseSymptomDao.findByField(Column.DISEASE_SYMPTOMS_COURSE_ID, treatmentCourseId);
        } catch (DaoException e) {
            throw new ServiceException("Can't get disease symptoms.", e);
        }
    }

    @Override
    public List<DrugRecipe> getDrugRecipes(int treatmentCourseId) throws ServiceException {
        AbstractDao<DrugRecipe> diseaseSymptomDao = new DrugRecipeDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(diseaseSymptomDao);
            return diseaseSymptomDao.findByField(Column.DRUG_RECIPES_TREATMENT_COURSE_ID, treatmentCourseId);
        } catch (DaoException e) {
            throw new ServiceException("Can't get drug recipes.", e);
        }
    }

    @Override
    public int saveTreatmentCourseAndGetId(TreatmentCourse treatmentCourse, List<DiseaseSymptom> diseasesSymptoms, List<DrugRecipe> drugsRecipes) throws ServiceException {
        TreatmentCourseDao treatmentCourseDao = new TreatmentCourseDaoImpl();
        DrugRecipeDao drugRecipeDao = new DrugRecipeDaoImpl();
        DiseaseSymptomDao diseaseSymptomDao = new DiseaseSymptomDaoImpl();
        try (DaoTransactionProvider transaction = new DaoTransactionProvider()) {
            transaction.initTransaction(false, treatmentCourseDao, diseaseSymptomDao, drugRecipeDao);

            int treatmentCourseId = treatmentCourseDao.saveAndGetId(treatmentCourse);

            for (DiseaseSymptom diseaseSymptom : diseasesSymptoms) {
                diseaseSymptom.setTreatmentCourseId(treatmentCourseId);
                diseaseSymptomDao.save(diseaseSymptom);
            }

            if (drugsRecipes.size() != 0) {
                for (DrugRecipe drugRecipe : drugsRecipes) {
                    drugRecipe.setTreatmentCourseId(treatmentCourseId);
                    drugRecipeDao.save(drugRecipe);
                }
            }
            transaction.commit();
            return treatmentCourseId;
        } catch (DaoException e) {
            throw new ServiceException("Can't get drug recipes.", e);
        }
    }
}
