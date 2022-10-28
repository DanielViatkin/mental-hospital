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
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GetAllDiseasesCommand implements Command {
    private final static DiseaseService diseaseService = DiseaseServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<Disease> diseaseList = diseaseService.getAll();

        int contentSize = ParameterExtractor.extractInt(RequestParameters.CONTENT_SIZE, requestContext);
        requestContext.addAttribute(RequestAttributes.CONTENT_SIZE, contentSize);

        int currentPage = ParameterExtractor.extractInt(RequestParameters.CURRENT_PAGE, requestContext);
        requestContext.addAttribute(RequestAttributes.CURRENT_PAGE, currentPage);

        requestContext.addAttribute(RequestAttributes.FULL_CONTENT_SIZE, diseaseList.size());
        List<Disease> diseaseListNeededSize = new ArrayList<>();
        for (int index = (currentPage-1)*contentSize; index < currentPage*contentSize && index < diseaseList.size(); index++){
            diseaseListNeededSize.add(diseaseList.get(index));
        }
        requestContext.addAttribute(RequestAttributes.DISEASES, diseaseListNeededSize);
        return CommandResult.forward(Page.DISEASES);
    }
}
