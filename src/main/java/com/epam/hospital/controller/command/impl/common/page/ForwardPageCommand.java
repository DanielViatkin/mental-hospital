package com.epam.hospital.controller.command.impl.common.page;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.request.RequestContext;

import java.util.HashMap;
import java.util.Map;

public class ForwardPageCommand implements Command {
    private final Map<String, String> commandPages = new HashMap<>();

    public ForwardPageCommand() {
        commandPages.put(CommandName.SIGN_UP_PAGE, Page.SIGN_UP_PAGE);
        commandPages.put(CommandName.LOGIN_PAGE, Page.LOGIN_PAGE);
        commandPages.put(CommandName.ADD_DOCTOR_PAGE, Page.ADD_DOCTOR_PAGE);
        commandPages.put(CommandName.ADD_DISEASE_PAGE, Page.ADD_DISEASE_PAGE);
        commandPages.put(CommandName.ADD_DRUG_PAGE, Page.ADD_DRUG_PAGE);
    }

    @Override
    public CommandResult execute(RequestContext requestContext) {
        String command = requestContext.getRequestParameter(RequestParameters.COMMAND);
        String page = commandPages.get(command);
        return CommandResult.forward(page);
    }
}
