package com.epam.hospital.controller.command.impl.admin;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Disease;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.DiseaseService;
import com.epam.hospital.service.logic.impl.DiseaseServiceImpl;

public class AddDiseaseCommand implements Command {
    private static final DiseaseService diseaseService = new DiseaseServiceImpl();
    private static final String DISEASE_COMMAND = "MentalHospital?command=" + CommandName.DISEASE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        Disease disease = Disease.builder()
                .name(ParameterExtractor.extractString(RequestParameters.NAME, requestContext))
                .description(ParameterExtractor.extractString(RequestParameters.DESCRIPTION, requestContext))
                .build();
        int id = diseaseService.saveAndGetId(disease);
        return CommandResult.redirect(DISEASE_COMMAND + "&id=" + id);
    }
}
