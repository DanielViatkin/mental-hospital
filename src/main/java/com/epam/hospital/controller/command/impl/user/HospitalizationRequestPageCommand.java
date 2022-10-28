package com.epam.hospital.controller.command.impl.user;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.hospital.type.ChamberType;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.ChamberService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.ChamberServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;

import java.util.List;

public class HospitalizationRequestPageCommand implements Command {
    private final ChamberService chamberService = ChamberServiceImpl.getInstance();
    private final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int doctorRoleId = userService.getDoctorRoleId();
        List<User> doctors = userService.getAllDoctors(doctorRoleId);
        requestContext.addAttribute(RequestAttributes.DOCTORS, doctors);

        List<ChamberType> chamberTypeList = chamberService.getAllChamberTypes();
        requestContext.addAttribute(RequestAttributes.CHAMBER_TYPES, chamberTypeList);

        return CommandResult.forward(Page.REQUEST_HOSPITALIZATION_PAGE);
    }
}
