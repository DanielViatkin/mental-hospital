package com.epam.hospital.controller.command.impl.doctor;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.DiseaseSymptom;
import com.epam.hospital.model.treatment.DrugRecipe;
import com.epam.hospital.model.treatment.TreatmentCourse;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ConsultationService;
import com.epam.hospital.service.logic.DiseaseService;
import com.epam.hospital.service.logic.DrugService;
import com.epam.hospital.service.logic.TreatmentCourseService;
import com.epam.hospital.service.logic.impl.ConsultationServiceImpl;
import com.epam.hospital.service.logic.impl.DiseaseServiceImpl;
import com.epam.hospital.service.logic.impl.DrugServiceImpl;
import com.epam.hospital.service.logic.impl.TreatmentCourseServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

//TESTED
@Slf4j
public class ConsultationCompleteCommand implements Command {
    private static final ConsultationService consultationService = ConsultationServiceImpl.getInstance();
    private static final TreatmentCourseService treatmentCourseService = TreatmentCourseServiceImpl.getInstance();
    private static final DiseaseService diseaseService = DiseaseServiceImpl.getInstance();
    private static final DrugService drugService = DrugServiceImpl.getInstance();
    private static final String CONSULTATION_PAGE_COMMAND = "MentalHospital?command=" + CommandName.CONSULTATION_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {

        List<String> diseasesNames = getStringList(RequestParameters.DISEASE, requestContext);

        List<Integer> diseasesId = new ArrayList<Integer>();
        for (String diseaseName : diseasesNames) {
            int diseaseId = diseaseService.getDiseaseIdByName(diseaseName);
            diseasesId.add(diseaseId);
        }

        List<String> symptoms = getStringList(RequestParameters.SYMPTOMS, requestContext);

        List<DiseaseSymptom> diseaseSymptoms = new ArrayList<DiseaseSymptom>();
        for (int i = 0; i < symptoms.size() && i < diseasesId.size(); i++) {
            DiseaseSymptom diseaseSymptom = DiseaseSymptom.builder()
                    .diseaseId(diseasesId.get(i))
                    .symptoms(symptoms.get(i))
                    .build();
            diseaseSymptoms.add(diseaseSymptom);
        }

        List<DrugRecipe> drugsRecipes = new ArrayList<>();
        List<String> drugsNames = getStringList(RequestParameters.DRUG, requestContext);
        if (drugsNames.size() > 0) {

            List<Integer> drugsId = new ArrayList<>();
            for (String drugName : drugsNames) {
                int drugId = drugService.getDrugIdByName(drugName);
                drugsId.add(drugId);
            }

            List<String> descriptions = getStringList(RequestParameters.DESCRIPTION, requestContext);

            List<Float> doses = new ArrayList<>();
            List<String> dosesListStr = getStringList(RequestParameters.DOSE, requestContext);
            for (String dose : dosesListStr) {
                doses.add(Float.parseFloat(dose));
            }

            for (int i = 0; i < drugsId.size() && i < descriptions.size() && i < doses.size(); i++) {
                DrugRecipe drugRecipe = DrugRecipe.builder()
                        .drugId(drugsId.get(i))
                        .description(descriptions.get(i))
                        .dose(doses.get(i))
                        .build();
                drugsRecipes.add(drugRecipe);
            }
        }

        TreatmentCourse treatmentCourse = TreatmentCourse.builder()
                .instruction(ParameterExtractor.extractString(RequestParameters.INSTRUCTION, requestContext))
                .build();

        int duration = ParameterExtractor.extractInt(RequestParameters.DURATION, requestContext);

        int treatmentCourseId = treatmentCourseService.saveTreatmentCourseAndGetId(treatmentCourse, diseaseSymptoms, drugsRecipes);
        int consultationId = ParameterExtractor.extractInt(RequestParameters.CONSULTATION_ID, requestContext);
        Consultation consultation = consultationService.getConsultationById(consultationId);
        consultation.setTreatmentCourseId(treatmentCourseId);
        consultation.setStatus(ConsultationStatus.COMPLETED);
        consultation.setDuration(duration);
        consultationService.update(consultation);

        return CommandResult.redirect(CONSULTATION_PAGE_COMMAND + "&id=" + consultationId);
    }

    private List<String> getStringList(String parameterName, RequestContext requestContext) {
        parameterName += "-";
        List<String> parameters = new ArrayList<String>();
        String parameter = "";
        for (int i = 0; ; i++) {
            parameter = ParameterExtractor.extractString(parameterName + i, requestContext);
            if (parameter != null) {
                parameters.add(parameter);
            } else {
                break;
            }
        }
        return parameters;
    }

}
