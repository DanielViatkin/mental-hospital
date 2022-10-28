package com.epam.hospital.controller.command.impl.doctor;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Consultation;
import com.epam.hospital.model.treatment.type.ConsultationStatus;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ConsultationService;
import com.epam.hospital.service.logic.impl.ConsultationServiceImpl;

public class ConsultationRejectCommand implements Command {
    private static final ConsultationService consultationService = ConsultationServiceImpl.getInstance();
    private static final String CONSULTATION_PAGE_COMMAND = "MentalHospital?command=" + CommandName.CONSULTATION_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int consultationId = ParameterExtractor.extractInt(RequestParameters.CONSULTATION_ID, requestContext);
        Consultation consultation = consultationService.getConsultationById(consultationId);
        consultation.setTreatmentCourseId(null);
        consultation.setStatus(ConsultationStatus.REJECTED);
        consultationService.update(consultation);
        return CommandResult.redirect(CONSULTATION_PAGE_COMMAND + "&id=" + consultationId);
    }
}
