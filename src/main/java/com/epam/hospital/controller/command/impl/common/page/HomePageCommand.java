package com.epam.hospital.controller.command.impl.common.page;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ChamberService;
import com.epam.hospital.service.logic.impl.ChamberServiceImpl;

import java.util.List;

public class HomePageCommand implements Command {
    private final static ChamberService chamberService = ChamberServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        List<ChamberType> chamberTypeList = chamberService.getAllChamberTypes();
        requestContext.addAttribute(RequestAttributes.CHAMBER_TYPES, chamberTypeList);
        return CommandResult.forward(Page.HOME_PAGE);
    }
}
