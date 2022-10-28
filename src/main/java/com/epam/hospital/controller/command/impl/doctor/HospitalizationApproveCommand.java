package com.epam.hospital.controller.command.impl.doctor;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.constant.web.SessionAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Hospitalization;
import com.epam.hospital.model.treatment.type.HospitalizationStatus;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.HospitalizationService;
import com.epam.hospital.service.logic.impl.HospitalizationServiceImpl;

public class HospitalizationApproveCommand implements Command {
    private static final String HOSPITALIZATION_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOSPITALIZATION_PAGE;

    HospitalizationService hospitalizationService = new HospitalizationServiceImpl();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {

        int doctorId = Integer.parseInt(requestContext.getSessionAttribute(SessionAttributes.USER_ID).toString());

        int hospitalizationId = ParameterExtractor.extractInt(RequestParameters.HOSPITALIZATION_ID, requestContext);
        Hospitalization hospitalization = hospitalizationService.getHospitalizationById(hospitalizationId);

        hospitalization.setDoctorId(doctorId);
        hospitalization.setStatus(HospitalizationStatus.APPROVED);
        hospitalizationService.update(hospitalization);
        return CommandResult.redirect(HOSPITALIZATION_PAGE_COMMAND + "&id=" + hospitalizationId);
    }
}
