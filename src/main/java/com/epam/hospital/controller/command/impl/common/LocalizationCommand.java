package com.epam.hospital.controller.command.impl.common;

import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.util.localization.PageMapper;

public class LocalizationCommand implements Command {
    private static final String RU = "ru";
    private static final String EN_LOCALE = "en_US";
    private static final String RU_LOCALE = "ru_RU";
    private static final PageMapper pageMapper = new PageMapper();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String language = ParameterExtractor.extractString(RequestParameters.LOCALE, requestContext);
        String locale = getLocaleByLanguage(language);
        requestContext.addSession(RequestAttributes.LANGUAGE, locale);
        String page = pageMapper.takePage(requestContext);
        return CommandResult.redirect(page);
    }

    private String getLocaleByLanguage(String language) {
        if (RU.equals(language)) {
            return RU_LOCALE;
        }
        return EN_LOCALE;
    }

}
