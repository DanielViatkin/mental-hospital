package com.epam.hospital.controller.command.impl.doctor;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.type.CommunicationType;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ConsultationService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.ConsultationServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsultationApproveCommand implements Command {
    private static final ConsultationService consultationService = ConsultationServiceImpl.getInstance();
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String CONSULTATION_PAGE_COMMAND = "MentalHospital?command=" + CommandName.CONSULTATION_PAGE;
    private static final String CONSULTATION_STATUS_APPROVED = "APPROVED";

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int consultationId = ParameterExtractor.extractInt(RequestParameters.CONSULTATION_ID, requestContext);
        Consultation consultation = consultationService.getConsultationById(consultationId);
        consultation.setTreatmentCourseId(null);

        ConsultationStatus consultationStatus = ConsultationStatus.valueOf(CONSULTATION_STATUS_APPROVED);
        consultation.setStatus(consultationStatus);

        DoctorInfo doctorInfo = userService.getDoctorInfoById(consultation.getDoctorId());
        consultation.setPrice(consultation.getCommunicationType().equals(CommunicationType.FACE_TO_FACE) ? doctorInfo.getPrice() : 0);
        consultationService.update(consultation);
        return CommandResult.redirect(CONSULTATION_PAGE_COMMAND + "&id=" + consultationId);
    }
}
