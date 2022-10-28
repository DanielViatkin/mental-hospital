package com.epam.hospital.controller.command.impl.common;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.SessionAttributes;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.service.exception.ServiceException;

public class SignOutCommand implements Command {
    private static final String HOME_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOME_PAGE;

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        requestContext.addSession(SessionAttributes.INVALIDATE_ATTRIBUTE, true);

        return CommandResult.redirect(HOME_PAGE_COMMAND);
    }
}
