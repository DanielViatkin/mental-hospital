package com.epam.hospital.controller.command.impl.user;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.Page;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.treatment.PatientCard;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.SignUpService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.SignUpServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;
import com.epam.hospital.util.Hasher;

import java.nio.charset.StandardCharsets;

public class SignUpCommand implements Command {
    public static final byte[] salt = "5e5db995-f84a-4a98-91ef-e6df62c491f1".getBytes(StandardCharsets.UTF_8);
    private static final String LOGIN_PAGE_COMMAND = "MentalHospital?command=" + CommandName.LOGIN_PAGE;
    private static final UserService userService = UserServiceImpl.getInstance();
    private static final String INVALID_DATA = "invalid.data";
    private static final String INVALID_LOGIN_KEY = "invalid.login";

    private final SignUpService signUpService = SignUpServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String login = ParameterExtractor.extractString(RequestParameters.LOGIN, requestContext);
        boolean isUserExist = userService.isUserExistByLogin(login);
        if (!isUserExist) {
            requestContext.addAttribute(RequestAttributes.LOGIN, login);

            String password = ParameterExtractor.extractString(RequestParameters.PASSWORD, requestContext);
            Hasher hasher = new Hasher();
            String hashedPassword = hasher.hashString(password, salt);
            User user = User.builder()
                    .email(login)
                    .hashedPassword(hashedPassword)
                    .firstName(ParameterExtractor.extractString(RequestParameters.FIRST_NAME, requestContext))
                    .isBanned(false)
                    .lastName(ParameterExtractor.extractString(RequestParameters.LAST_NAME, requestContext))
                    .userRoleId(userService.getUserRoleId())
                    .number(ParameterExtractor.extractString(RequestParameters.PHONE_NUMBER, requestContext))
                    .build();
            PatientCard patientCard = PatientCard.builder()
                    .age(ParameterExtractor.extractInt(RequestParameters.AGE, requestContext))
                    .sex(ParameterExtractor.extractString(RequestParameters.SEX, requestContext))
                    .spareNumber(ParameterExtractor.extractString(RequestParameters.SPARE_PHONE_NUMBER, requestContext))
                    .build();

            boolean isDone = signUpService.signUp(user, patientCard);
            if (isDone) {
                return CommandResult.redirect(LOGIN_PAGE_COMMAND);
            } else {
                requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, INVALID_DATA);
            }
        } else {
            requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, INVALID_LOGIN_KEY);
        }
        String firstName = ParameterExtractor.extractString(RequestParameters.FIRST_NAME, requestContext);
        String lastName = ParameterExtractor.extractString(RequestParameters.FIRST_NAME, requestContext);
        requestContext.addAttribute(RequestParameters.LOGIN, login);
        requestContext.addAttribute(RequestParameters.FIRST_NAME, firstName);
        requestContext.addAttribute(RequestParameters.LAST_NAME, lastName);

        return CommandResult.forward(Page.SIGN_UP_PAGE);
    }
}
