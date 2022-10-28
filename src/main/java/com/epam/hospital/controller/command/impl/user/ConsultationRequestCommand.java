package com.epam.hospital.controller.command.impl.user;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.constant.web.SessionAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ConsultationService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.ConsultationServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class ConsultationRequestCommand implements Command {
    private static final String CONSULTATION_PENDING_STATUS = "PENDING";
    private static final String CONSULTATION_REQUEST_PAGE_COMMAND = "MentalHospital?command=" + CommandName.CONSULTATION_PAGE;
    private static final ConsultationService consultationService = ConsultationServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {

        String patentCardIdStr = requestContext.getSessionAttribute(SessionAttributes.PATIENT_CARD_ID).toString();
        int patientCardId = Integer.parseInt(patentCardIdStr);
        String communicationTypeStr = ParameterExtractor.extractString(RequestParameters.IS_ONLINE, requestContext);
        CommunicationType communicationType;
        if (communicationTypeStr != null) {
            communicationType = CommunicationType.ONLINE;
        } else {
            communicationType = CommunicationType.FACE_TO_FACE;
        }

        Integer parentConsultationId = null;
        User doctor;
        Consultation parentConsultation = null;
        if (requestContext.getRequestParameter(RequestParameters.PARENT_ID) == null) {
            String doctorName = ParameterExtractor.extractString(RequestParameters.DOCTOR, requestContext);
            List<String> fullName = new ArrayList<>(Arrays.asList(doctorName.split(" ")));
            doctor = userService.getUserByFullName(fullName.get(0), fullName.get(1));
        } else {
            parentConsultationId = ParameterExtractor.extractInt(RequestParameters.PARENT_ID, requestContext);
            parentConsultation = consultationService.getConsultationById(parentConsultationId);
            doctor = userService.getUserById(parentConsultation.getDoctorId());
        }

        Consultation consultation = Consultation.builder()
                .communicationType(communicationType)
                .date(ParameterExtractor.extractTimestamp(RequestParameters.DATE, requestContext))
                .doctorId(doctor.getUserId())
                .status(ConsultationStatus.valueOf(CONSULTATION_PENDING_STATUS))
                .patientId(patientCardId)
                .treatmentCourseId(null)
                .duration(0)
                .price(0.0)
                .parentConsultationId(parentConsultationId)
                .build();
        int consultationId = consultationService.saveAndGetId(consultation);
        if (parentConsultation != null) {
            parentConsultation.setChildConsultationId(consultationId);
            consultationService.update(parentConsultation);
        }
        return CommandResult.redirect(CONSULTATION_REQUEST_PAGE_COMMAND + "&id=" + consultationId);
    }
}
