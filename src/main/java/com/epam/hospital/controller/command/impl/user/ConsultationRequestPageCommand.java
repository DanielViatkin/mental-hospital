package com.epam.hospital.controller.command.impl.user;

import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.UserServiceImpl;

import java.util.List;

public class ConsultationRequestPageCommand implements Command {
    private static final UserService userService = UserServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int doctorRoleId = userService.getDoctorRoleId();
        List<User> doctors = userService.getAllDoctors(doctorRoleId);
        requestContext.addAttribute(RequestAttributes.DOCTORS, doctors);

        return CommandResult.forward(Page.REQUEST_CONSULTATION_PAGE);
    }
}
