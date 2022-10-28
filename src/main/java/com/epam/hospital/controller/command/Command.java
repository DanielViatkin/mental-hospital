package com.epam.hospital.controller.command;

import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.service.exception.ServiceException;

public interface Command {
    CommandResult execute(RequestContext requestContext) throws ServiceException;
}
