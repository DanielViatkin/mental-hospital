package com.epam.hospital.service.logic;

import com.epam.hospital.model.treatment.DiseaseSymptom;
import com.epam.hospital.model.treatment.DrugRecipe;
import com.epam.hospital.model.treatment.TreatmentCourse;
import com.epam.hospital.service.exception.ServiceException;

import java.util.List;

public interface TreatmentCourseService {
    TreatmentCourse getTreatmentCourseById(int treatmentCourseId) throws ServiceException;

    List<DiseaseSymptom> getDiseaseSymptoms(int treatmentCourseId) throws ServiceException;

    List<DrugRecipe> getDrugRecipes(int treatmentCourseId) throws ServiceException;

    int saveTreatmentCourseAndGetId(TreatmentCourse treatmentCourse, List<DiseaseSymptom> diseasesSymptoms, List<DrugRecipe> drugsRecipes) throws ServiceException;
}
