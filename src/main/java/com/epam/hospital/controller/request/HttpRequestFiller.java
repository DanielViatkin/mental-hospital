package com.epam.hospital.controller.request;

import com.epam.hospital.constant.web.SessionAttributes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Set;

public class HttpRequestFiller {

    public void fillData(HttpServletRequest request, RequestContext requestContext) {
        Set<String> requestAttributeNames = requestContext.getRequestAttributeNames();
        for (String attributeName : requestAttributeNames) {
            Object attributeValue = requestContext.getRequestAttribute(attributeName);
            request.setAttribute(attributeName, attributeValue);
        }

        HttpSession session = request.getSession();
        Set<String> sessionAttributeNames = requestContext.getSessionAttributeNames();
        if (sessionAttributeNames.contains(SessionAttributes.INVALIDATE_ATTRIBUTE)) {
            session.invalidate();
        } else {
            for (String attributeName : sessionAttributeNames) {
                Object attributeValue = requestContext.getSessionAttribute(attributeName);
                session.setAttribute(attributeName, attributeValue);
            }
        }
    }
}
