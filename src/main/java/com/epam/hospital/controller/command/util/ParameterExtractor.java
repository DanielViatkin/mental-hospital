package com.epam.hospital.controller.command.util;

import com.epam.hospital.controller.request.RequestContext;
import lombok.extern.slf4j.Slf4j;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class ParameterExtractor {

    public static String extractString(String parameterName, RequestContext requestContext) {
        return requestContext.getRequestParameter(parameterName);
    }

    public static int extractInt(String parameterName, RequestContext requestContext) {
        return Integer.parseInt(requestContext.getRequestParameter(parameterName));
    }

    public static Date extractDate(String parameterName, RequestContext requestContext) {
        SimpleDateFormat utilDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date paramValue = null;
        try {
            paramValue = utilDateFormatter.parse(requestContext.getRequestParameter(parameterName));
        } catch (Exception e) {
            log.error("Input date from html form is wrong");
        }
        return paramValue;
    }

    public static double extractDouble(String parameterName, RequestContext requestContext) {
        return Double.parseDouble(requestContext.getRequestParameter(parameterName));
    }

    public static Timestamp extractTimestamp(String parameterName, RequestContext requestContext) {
        Date date = extractDate(parameterName, requestContext);
        return new Timestamp(date.getTime());
    }
}
