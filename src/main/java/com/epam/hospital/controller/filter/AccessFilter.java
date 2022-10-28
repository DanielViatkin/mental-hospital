package com.epam.hospital.controller.filter;

import com.epam.hospital.constant.web.CommandName;
import com.epam.hospital.constant.web.RequestAttributes;
import com.epam.hospital.constant.web.RequestParameters;
import com.epam.hospital.constant.web.SessionAttributes;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AccessFilter implements Filter {
    private static final String GUEST_ROLE = "GUEST";
    private static final String USER_ROLE = "USER";
    private static final String DOCTOR_ROLE = "DOCTOR";
    private static final String ADMIN_ROLE = "ADMIN";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        String commandName = servletRequest.getParameter(RequestParameters.COMMAND);
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();
        Object role = session.getAttribute(SessionAttributes.ROLE);

        if (commandName != null) {
            if (role == null) {
                role = "GUEST";
            }
            System.out.println(role);
            boolean isAccessAllowed = isAccessAllowed(commandName, role.toString());
            if (!isAccessAllowed) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
                servletRequest.setAttribute(RequestAttributes.ERROR_MESSAGE, "Permission denied");
                return;
            }
        }
        doNextFilter(servletRequest, servletResponse, filterChain);
    }

    private void doNextFilter(ServletRequest servletRequest,
                              ServletResponse servletResponse,
                              FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isAccessAllowed(String commandName, String role) {
        if (commandName == null) {
            return true;
        }
        if (role.equals(GUEST_ROLE)) {
            return CommandName.SIGN_UP.equals(commandName) ||
                    CommandName.SIGN_UP_PAGE.equals(commandName) ||
                    CommandName.HOME_PAGE.equals(commandName) ||
                    CommandName.DISEASES.equals(commandName) ||
                    CommandName.DISEASE.equals(commandName) ||
                    CommandName.DOCTORS.equals(commandName) ||
                    CommandName.LOGIN.equals(commandName) ||
                    CommandName.LOCALIZATION.equals(commandName) ||
                    CommandName.PROFILE_PAGE.equals(commandName) ||
                    CommandName.LOGIN_PAGE.equals(commandName);
        }

        if (role.equals(USER_ROLE)) {
            return CommandName.CONSULTATION_PAGE.equals(commandName) ||
                    CommandName.CONSULTATION_REQUEST_PAGE.equals(commandName) ||
                    CommandName.HOSPITALIZATION_REQUEST_PAGE.equals(commandName) ||
                    CommandName.HOSPITALIZATION_REQUEST.equalsIgnoreCase(commandName) ||
                    CommandName.HOSPITALIZATION_PAGE.equalsIgnoreCase(commandName) ||
                    CommandName.HOME_PAGE.equals(commandName) ||
                    CommandName.DISEASES.equals(commandName) ||
                    CommandName.DISEASE.equals(commandName) ||
                    CommandName.DOCTORS.equals(commandName) ||
                    CommandName.SIGN_OUT.equals(commandName) ||
                    CommandName.LOCALIZATION.equals(commandName) ||
                    CommandName.PROFILE_PAGE.equals(commandName) ||
                    CommandName.CONSULTATION_REQUEST.equals(commandName) ||
                    CommandName.LOGIN_PAGE.equals(commandName);
        }

        if (role.equals(DOCTOR_ROLE)) {
            return CommandName.CONSULTATION_PAGE.equals(commandName) ||
                    CommandName.HOSPITALIZATION_PAGE.equalsIgnoreCase(commandName) ||
                    CommandName.HOSPITALIZATION_APPROVE.equalsIgnoreCase(commandName) ||
                    CommandName.HOSPITALIZATION_REJECT.equalsIgnoreCase(commandName) ||
                    CommandName.HOSPITALIZATION_COMPLETE.equalsIgnoreCase(commandName) ||
                    CommandName.DOCTORS.equals(commandName) ||
                    CommandName.DISEASES.equals(commandName) ||
                    CommandName.DISEASE.equals(commandName) ||
                    CommandName.LOCALIZATION.equals(commandName) ||
                    CommandName.PROFILE_PAGE.equals(commandName) ||
                    CommandName.HOME_PAGE.equals(commandName) ||
                    CommandName.SIGN_OUT.equals(commandName) ||
                    CommandName.CONSULTATION_COMPLETE.equals(commandName) ||
                    CommandName.CONSULTATION_REJECT.equals(commandName) ||
                    CommandName.CONSULTATION_APPROVE.equals(commandName) ||
                    CommandName.CONSULTATION_COMPLETE_WITHOUT_COURSE.equals(commandName);
        }
        if (role.equals(ADMIN_ROLE)) {
            return CommandName.USERS.equals(commandName) ||
                    CommandName.BAN.equalsIgnoreCase(commandName) ||
                    CommandName.UNBAN.equalsIgnoreCase(commandName) ||
                    CommandName.ADD_DRUG_PAGE.equals(commandName) ||
                    CommandName.ADD_DISEASE_PAGE.equals(commandName) ||
                    CommandName.ADD_DOCTOR_PAGE.equals(commandName) ||
                    CommandName.ADD_DRUG.equals(commandName) ||
                    CommandName.ADD_DOCTOR.equals(commandName) ||
                    CommandName.ADD_DISEASE.equals(commandName) ||
                    CommandName.HOME_PAGE.equals(commandName) ||
                    CommandName.LOCALIZATION.equals(commandName) ||
                    CommandName.DOCTORS.equals(commandName) ||
                    CommandName.DISEASES.equals(commandName) ||
                    CommandName.DISEASE.equals(commandName) ||
                    CommandName.PROFILE_PAGE.equals(commandName) ||
                    CommandName.SIGN_OUT.equals(commandName) ||
                    CommandName.HOSPITALIZATION_PAGE.equals(commandName) ||
                    CommandName.CONSULTATION_PAGE.equals(commandName) ||
                    CommandName.LOGIN.equals(commandName);
        }

        return true;
    }

    @Override
    public void destroy() {

    }

}
