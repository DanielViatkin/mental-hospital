package com.epam.hospital.controller.command.impl.admin;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.controller.command.Command;
import com.epam.hospital.controller.command.CommandResult;
import com.epam.hospital.controller.command.util.ParameterExtractor;
import com.epam.hospital.controller.request.RequestContext;
import com.epam.hospital.model.user.User;
import com.epam.hospital.model.user.info.DoctorInfo;
import com.epam.hospital.service.exception.ServiceException;
import com.epam.hospital.service.logic.AdminService;
import com.epam.hospital.service.logic.UserService;
import com.epam.hospital.service.logic.impl.AdminServiceImpl;
import com.epam.hospital.service.logic.impl.UserServiceImpl;
import com.epam.hospital.util.Hasher;

import java.nio.charset.StandardCharsets;


public class AddDoctorCommand implements Command {
    static final byte[] salt = "5e5db995-f84a-4a98-91ef-e6df62c491f1".getBytes(StandardCharsets.UTF_8);
    private static final AdminService adminService = new AdminServiceImpl();
    private static final UserService userService = new UserServiceImpl();
    private static final String ADD_DOCTOR_COMMAND = "MentalHospital?command=" + CommandName.ADD_USERS;
    private static final String USER_PAGE_COMMAND = "MentalHospital?command=" + CommandName.PROFILE_PAGE;
    private static final String INVALID_LOGIN_KEY = "invalid.login";


    @Override
    public CommandResult execute(RequestContext requestContext) throws ServiceException {
        String login = ParameterExtractor.extractString(RequestParameters.LOGIN, requestContext);
        boolean isUserExist = userService.isUserExistByLogin(login);
        if (!isUserExist) {
            String password = ParameterExtractor.extractString(RequestParameters.PASSWORD, requestContext);
            Hasher hasher = new Hasher();
            String hashedPassword = hasher.hashString(password, salt);

            int doctorRoleId = userService.getDoctorRoleId();

            User user = User.builder()
                    .number(ParameterExtractor.extractString(RequestParameters.PHONE_NUMBER, requestContext))
                    .userRoleId(doctorRoleId)
                    .isBanned(false)
                    .email(ParameterExtractor.extractString(RequestParameters.LOGIN, requestContext))
                    .firstName(ParameterExtractor.extractString(RequestParameters.FIRST_NAME, requestContext))
                    .lastName(ParameterExtractor.extractString(RequestParameters.LAST_NAME, requestContext))
                    .hashedPassword(hashedPassword)
                    .build();

            DoctorInfo doctorInfo = DoctorInfo.builder()
                    .classification(ParameterExtractor.extractInt(RequestParameters.CLASSIFICATION, requestContext))
                    .specialization(ParameterExtractor.extractString(RequestParameters.SPECIALIZATION, requestContext))
                    .workExperience(ParameterExtractor.extractInt(RequestParameters.WORK_EXPERIENCE, requestContext))
                    .price(ParameterExtractor.extractDouble(RequestParameters.PRICE, requestContext))
                    .build();
            int id = adminService.saveDoctorAndGetId(user, doctorInfo);

            return CommandResult.redirect(USER_PAGE_COMMAND + "&id=" + id + "&content=consultations&content-size=5&current-page=1");
        } else {
            requestContext.addAttribute(RequestAttributes.ERROR_MESSAGE, INVALID_LOGIN_KEY);
        }
        return CommandResult.forward(ADD_DOCTOR_COMMAND);
    }
}
