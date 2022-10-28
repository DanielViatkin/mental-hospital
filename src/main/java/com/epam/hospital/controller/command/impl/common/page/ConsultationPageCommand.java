package com.epam.hospital.controller.command.impl.common.page;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.dto.ConsultationDto;
import com.epam.hospital.model.dto.DiseaseWithSymptomsDto;
import com.epam.hospital.model.dto.DrugRecipeDto;
import com.epam.hospital.model.treatment.*;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.*;
import com.epam.hospital.service.logic.impl.*;

import java.util.ArrayList;
import java.util.List;

public class ConsultationPageCommand implements Command {
    private final static ConsultationService consultationService = ConsultationServiceImpl.getInstance();
    private final static TreatmentCourseService treatmentCourseService = TreatmentCourseServiceImpl.getInstance();
    private final static UserService userService = UserServiceImpl.getInstance();
    private final static PatientCardService patientCardService = PatientCardServiceImpl.getInstance();
    private final static DiseaseService diseaseService = DiseaseServiceImpl.getInstance();
    private final static DrugService drugService = DrugServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int consultationId = ParameterExtractor.extractInt(RequestParameters.CONSULTATION_ID, requestContext);

        List<Consultation> consultations = consultationService.getAllRelatedConsultations(consultationId);
        requestContext.addAttribute(RequestAttributes.CONSULTATION_ID, consultations.get(consultations.size() - 1).getConsultationId());
        List<ConsultationDto> consultationDtoList = new ArrayList<>(consultations.size());
        for (Consultation consultation : consultations) {
            User doctor = userService.getUserById(consultation.getDoctorId());
            int patientId = patientCardService.getPatientCardById(consultation.getPatientId()).getUserId();
            User patient = userService.getUserById(patientId);

            ConsultationDto.ConsultationDtoBuilder consultationDtoBuilder = ConsultationDto.builder()
                    .communicationType(consultation.getCommunicationType().equals(CommunicationType.FACE_TO_FACE) ? "FACE TO FACE" : "ONLINE")
                    .date(consultation.getDate())
                    .patientId(patient.getUserId())
                    .doctorId(doctor.getUserId())
                    .duration(consultation.getDuration())
                    .doctorFirstName(doctor.getFirstName())
                    .doctorLastName(doctor.getLastName())
                    .patientFirstName(patient.getFirstName())
                    .patientLastName(patient.getLastName())
                    .price(consultation.getPrice())
                    .consultationStatus(consultation.getStatus());


            if (consultation.getStatus().equals(ConsultationStatus.COMPLETED)) {
                TreatmentCourse treatmentCourse;
                try {
                    treatmentCourse = treatmentCourseService.getTreatmentCourseById(consultation.getTreatmentCourseId());
                    consultationDtoBuilder.courseExist(true);
                    List<DiseaseSymptom> diseaseSymptoms = treatmentCourseService.getDiseaseSymptoms(treatmentCourse.getTreatmentCourseId());
                    List<DiseaseWithSymptomsDto> diseaseWithSymptomsDtos = new ArrayList<>();
                    for (DiseaseSymptom diseaseSymptom : diseaseSymptoms) {
                        int diseaseId = diseaseSymptom.getDiseaseId();
                        Disease disease = diseaseService.getDiseaseById(diseaseId);
                        DiseaseWithSymptomsDto diseaseWithSymptomsDto = DiseaseWithSymptomsDto.builder()
                                .name(disease.getName())
                                .id(diseaseId)
                                .symptoms(diseaseSymptom.getSymptoms())
                                .build();
                        diseaseWithSymptomsDtos.add(diseaseWithSymptomsDto);
                    }

                    List<DrugRecipe> drugRecipes = treatmentCourseService.getDrugRecipes(treatmentCourse.getTreatmentCourseId());
                    List<DrugRecipeDto> drugDtoWithDozes = new ArrayList<>();
                    for (DrugRecipe drugRecipe : drugRecipes) {
                        int drugId = drugRecipe.getDrugId();
                        Drug drugById = drugService.getDrugById(drugId);
                        String name = drugById.getName();
                        DrugRecipeDto drugDtoWithDoze = DrugRecipeDto.builder().
                                name(name)
                                .doze(drugRecipe.getDose())
                                .description(drugRecipe.getDescription())
                                .build();
                        drugDtoWithDozes.add(drugDtoWithDoze);
                    }
                    consultationDtoBuilder
                            .diseases(diseaseWithSymptomsDtos)
                            .drugs(drugDtoWithDozes)
                            .price(consultation.getPrice())

                            .instruction(treatmentCourse.getInstruction());

                } catch (ServiceException serviceException) {
                    consultationDtoBuilder.courseExist(false);
                }
            }
            ConsultationDto consultationDto = consultationDtoBuilder.build();
            consultationDtoList.add(consultationDto);
        }
        requestContext.addAttribute(RequestAttributes.CONSULTATION_PIPELINE, consultationDtoList);
        return CommandResult.forward(Page.CONSULTATION_PAGE);
    }
}
