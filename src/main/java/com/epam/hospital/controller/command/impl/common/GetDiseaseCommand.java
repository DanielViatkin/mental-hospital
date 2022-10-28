package com.epam.hospital.controller.command.impl.common;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.Disease;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.DiseaseService;
import com.epam.hospital.service.logic.impl.DiseaseServiceImpl;


public class GetDiseaseCommand implements Command {
    private static final DiseaseService diseaseService = DiseaseServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int diseaseId = ParameterExtractor.extractInt(RequestParameters.ID, requestContext);
        Disease disease = diseaseService.getDiseaseById(diseaseId);

        requestContext.addAttribute(RequestAttributes.DISEASE, disease);

        return CommandResult.forward(Page.DISEASE);
    }
}
