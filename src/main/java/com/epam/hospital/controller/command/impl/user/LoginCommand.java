package com.epam.hospital.controller.command.impl.user;

import com.epam.hospital.constant.web.*;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.user.User;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.PatientCardService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.PatientCardServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;
import com.sun.source.tree.Tree;

import java.util.Optional;


public class LoginCommand implements Command {
    private static final String INCORRECT_DATA_KEY = "incorrect.data.key";
    private static final String BANNED_USER_KEY = "banned";
    private static final String USER_ROLE = "USER";
    private static final String HOME_PAGE_COMMAND = "MentalHospital?command=" + CommandName.HOME_PAGE;


    private static final UserService userService = UserServiceImpl.getInstance();
    private static final PatientCardService patientCardService = PatientCardServiceImpl.getInstance();

    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {

        String email = ParameterExtractor.extractString(RequestParameters.LOGIN, requestContext);
        String password = ParameterExtractor.extractString(RequestParameters.PASSWORD, requestContext);

        Optional<User> userOptional = userService.login(email, password);
        if (!userOptional.isEmpty()) {
            User user = userOptional.get();
            if (!user.getIsBanned()) {
                String role = userService.getUserRoleById(user.getUserRoleId());
                requestContext.addSession(SessionAttributes.USER_ID, user.getUserId());
                requestContext.addSession(SessionAttributes.ROLE, role);
                if (role.equals(USER_ROLE)) {
                    int patientCardId = patientCardService.getPatientCardIdByUserId(user.getUserId());
                    requestContext.addSession(RequestAttributes.PATIENT_CARD_ID, patientCardId);
                }
                return CommandResult.redirect(HOME_PAGE_COMMAND);
            } else {
                requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, BANNED_USER_KEY);
            }
        } else {
            requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, INCORRECT_DATA_KEY);
        }
        requestContext.addAttribute(RequestAttributes.LOGIN, email);
        return CommandResult.forward(Page.LOGIN_PAGE);

    }
}
