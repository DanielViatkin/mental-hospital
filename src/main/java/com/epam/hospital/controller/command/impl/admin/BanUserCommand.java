package com.epam.hospital.controller.command.impl.admin;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.AdminService;
import com.epam.hospital.service.logic.impl.AdminServiceImpl;

public class BanUserCommand implements Command {
    private static final AdminService adminService = AdminServiceImpl.getInstance();
    private static final String USER_ALREADY_BANNED = "user already banned";
    private static final String ALL_USERS_COMMAND = "MentalHospital?command=" + CommandName.USERS;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        int userId = ParameterExtractor.extractInt(RequestParameters.ID, requestContext);
        if (!adminService.isUserBanned(userId)) {
            adminService.setUserBanStatusById(userId, true);
        } else {
            requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, USER_ALREADY_BANNED);
        }
        return CommandResult.redirect(ALL_USERS_COMMAND);
    }
}
