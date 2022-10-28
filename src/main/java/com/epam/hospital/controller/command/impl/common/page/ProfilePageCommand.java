package com.epam.hospital.controller.command.impl.common.page;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.dto.*;
import com.epam.hospital.model.treatment.ChamberStaying;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.*;
import com.epam.hospital.service.logic.impl.*;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProfilePageCommand implements Command {
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final PatientCardService patientCardService = PatientCardServiceImpl.getInstance();
    private static final ConsultationService consultationService = ConsultationServiceImpl.getInstance();
    private static final HospitalizationService hospitalizationService = HospitalizationServiceImpl.getInstance();
    private static final ChamberService chamberService = ChamberServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String content = ParameterExtractor.extractString(RequestParameters.CONTENT, requestContext);
        requestContext.addAttribute(RequestAttributes.CONTENT, content);

        int contentSize = ParameterExtractor.extractInt(RequestParameters.CONTENT_SIZE, requestContext);
        requestContext.addAttribute(RequestAttributes.CONTENT_SIZE, contentSize);

        int currentPage = ParameterExtractor.extractInt(RequestParameters.CURRENT_PAGE, requestContext);
        requestContext.addAttribute(RequestAttributes.CURRENT_PAGE, currentPage);

        int profileId = ParameterExtractor.extractInt(RequestParameters.ID, requestContext);
        requestContext.addAttribute(RequestAttributes.PROFILE_ID, profileId);

        User user = userService.getUserById(profileId);
        String role = userService.getUserRoleById(user.getUserRoleId());

        UserInfoDto userInfoDto = UserInfoDto.builder()
                .fullName(user.getFirstName() + " " + user.getLastName())
                .role(role)
                .id(profileId)
                .status(user.getIsBanned() ? "BANNED" : "ACTIVE")
                .email(user.getEmail())
                .build();
        requestContext.addAttribute(RequestAttributes.USER_INFO, userInfoDto);

        List<Consultation> consultations;
        List<Hospitalization> hospitalizations;
        List<ChamberStaying> chamberStayings;
        if (role.equalsIgnoreCase("USER")) {
            int patientCardId = patientCardService.getPatientCardIdByUserId(user.getUserId());
            PatientCard patientCard = patientCardService.getPatientCardById(patientCardId);
            PatientInfoDto patientInfoDto = PatientInfoDto.builder()
                    .age(patientCard.getAge())
                    .spareNumber(patientCard.getSpareNumber())
                    .build();
            requestContext.addAttribute(RequestAttributes.PATIENT_INFO, patientInfoDto);

            consultations = consultationService.getAllConsultationsByPatientCardId(patientCard.getCardId());

            hospitalizations = hospitalizationService.getAllHospitalizationsByPatientCardId(patientCard.getCardId());
            chamberStayings = getAllChamberStayings(hospitalizations);
        } else {
            DoctorInfo doctorInfo = userService.getDoctorInfoById(profileId);
            DoctorInfoDto doctorInfoDto = DoctorInfoDto.builder()
                    .specialization(doctorInfo.getSpecialization())
                    .classification(doctorInfo.getClassification())
                    .workExperience(doctorInfo.getWorkExperience())
                    .price(doctorInfo.getPrice())
                    .build();

            requestContext.addAttribute(RequestAttributes.DOCTOR_INFO, doctorInfoDto);

            consultations = consultationService.getAllConsultationsByDoctorId(profileId);
            hospitalizations = hospitalizationService.getAllHospitalizationsByDoctorCardId(profileId);
            chamberStayings = getAllChamberStayings(hospitalizations);
        }

        if (content.equals("consultations")) {
            requestContext.addAttribute(RequestAttributes.FULL_CONTENT_SIZE, consultations.size());
            List<ShortConsultationDto> consultationDtoList = new ArrayList<>();

            Consultation consultation;
            for (int index = (currentPage - 1) * contentSize; index < contentSize * currentPage && index < consultations.size(); index++) {
                consultation = consultations.get(index);
                ShortConsultationDto.ShortConsultationDtoBuilder shortConsultationDtoBuilder = ShortConsultationDto.builder()
                        .date(consultation.getDate())
                        .communicationType(consultation.getCommunicationType().equals(CommunicationType.FACE_TO_FACE) ? "FACE TO FACE" : "ONLINE")
                        .consultationStatus(consultation.getStatus())
                        .id(consultation.getConsultationId());
                if (role.equalsIgnoreCase("USER")) {
                    User doctor = userService.getUserById(consultation.getDoctorId());
                    shortConsultationDtoBuilder.doctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
                } else {
                    PatientCard patientCard = patientCardService.getPatientCardById(consultation.getPatientId());
                    User patient = userService.getUserById(patientCard.getUserId());
                    shortConsultationDtoBuilder.patientFullName(patient.getFirstName() + " " + patient.getLastName());
                }
                consultationDtoList.add(shortConsultationDtoBuilder.build());
            }
            requestContext.addAttribute(RequestAttributes.CONSULTATIONS, consultationDtoList);
        } else {
            requestContext.addAttribute(RequestAttributes.FULL_CONTENT_SIZE, hospitalizations.size());
            List<ShortHospitalizationDto> hospitalizationDtoList = new ArrayList<>();

            for (int i = (currentPage - 1) * contentSize; i < hospitalizations.size() && i < chamberStayings.size() && i < contentSize * currentPage; i++) {
                ShortHospitalizationDto.ShortHospitalizationDtoBuilder shortHospitalizationDtoBuilder = ShortHospitalizationDto.builder()
                        .id(hospitalizations.get(i).getId())
                        .hospitalizationStatus(hospitalizations.get(i).getStatus())
                        .date(chamberStayings.get(i).getDateIn())
                        .chamberNumber(chamberStayings.get(i).getChamberId());

                if (role.equalsIgnoreCase("USER")) {
                    User doctor = userService.getUserById(hospitalizations.get(i).getDoctorId());
                    shortHospitalizationDtoBuilder.doctorFullName(doctor.getFirstName() + " " + doctor.getLastName());
                } else {
                    PatientCard patientCard = patientCardService.getPatientCardById(hospitalizations.get(i).getPatientId());
                    User patient = userService.getUserById(patientCard.getUserId());
                    shortHospitalizationDtoBuilder.patientFullName(patient.getFirstName() + " " + patient.getLastName());
                }
                hospitalizationDtoList.add(shortHospitalizationDtoBuilder.build());
            }
            requestContext.addAttribute(RequestAttributes.HOSPITALIZATIONS, hospitalizationDtoList);
        }
        return CommandResult.forward(Page.PROFILE_PAGE);
    }

    private List<ChamberStaying> getAllChamberStayings(List<Hospitalization> hospitalizations) throws ServiceException {
        List<ChamberStaying> stayings = new ArrayList<>();
        for (Hospitalization hospitalization : hospitalizations) {
            ChamberStaying chamberStaying = chamberService.getChamberStayingByHospitalizationId(hospitalization.getId());
            stayings.add(chamberStaying);
        }
        return stayings;
    }
}
