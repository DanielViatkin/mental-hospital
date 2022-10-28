package com.epam.hospital.util.localization;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.constant.web.SessionAttributes;
import com.epam.hospital.controller.request.RequestContext;

public class PageMapper {
    private static final String PAGE = "MentalHospital?command=";
    private static final String LOGIN_PAGE = "MentalHospital?command=";
    private static final String PARAMETER_SPLITERATOR = "&";
    private static final String COMMAND = "command";

    public String takePage(RequestContext requestContext) {
        String url = requestContext.getHeader();
        String action = extractCommand(url);

        if (action.equals(CommandName.CONSULTATION_APPROVE) ||
                action.equals(CommandName.CONSULTATION_COMPLETE) ||
                action.equals(CommandName.CONSULTATION_REJECT)) {
            return PAGE + CommandName.CONSULTATION_PAGE + PARAMETER_SPLITERATOR +
                    "id=" + requestContext.getSessionAttribute(SessionAttributes.DOCTOR_ID);
        }
        if (action.equals(CommandName.CONSULTATION_REQUEST)) {
            return PAGE + CommandName.CONSULTATION_REQUEST_PAGE;
        }
        if (action.equals(CommandName.HOSPITALIZATION_COMPLETE) ||
                action.equals(CommandName.HOSPITALIZATION_REJECT) ||
                action.equals(CommandName.HOSPITALIZATION_APPROVE)) {
            return PAGE + CommandName.PROFILE_PAGE + PARAMETER_SPLITERATOR +
                    "id=" + requestContext.getSessionAttribute(SessionAttributes.DOCTOR_ID);
        }
        if (action.equals(CommandName.HOSPITALIZATION_REQUEST)) {
            return PAGE + CommandName.HOSPITALIZATION_REQUEST_PAGE;
        }
        if (action.equals(CommandName.LOGIN)) {
            return PAGE + CommandName.LOGIN_PAGE;
        }
        if (action.equals(CommandName.SIGN_UP)) {
            return PAGE + CommandName.SIGN_UP_PAGE;
        }
        if (action.contains(CommandName.BAN) ||
                action.contains(CommandName.UNBAN)) {
            return PAGE + CommandName.USERS;
        }
        if (url.contains(COMMAND)) {
            return url;
        } else {
            return PAGE + CommandName.HOME_PAGE;
        }
    }

    private String extractCommand(String url) {
        int commandIndex = url.indexOf(RequestParameters.COMMAND) + RequestParameters.COMMAND.length() + 1;
        int lastCommandIndex = url.indexOf(PARAMETER_SPLITERATOR);
        if (lastCommandIndex == -1) {
            return url.substring(commandIndex);
        } else {
            return url.substring(commandIndex, lastCommandIndex);
        }
    }
}
