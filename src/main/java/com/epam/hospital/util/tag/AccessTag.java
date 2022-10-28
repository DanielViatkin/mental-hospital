package com.epam.hospital.util.tag;

import com.epam.hospital.constant.web.SessionAttributes;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.jsp.tagext.TagSupport;

public class AccessTag extends TagSupport {
    private static final String GUEST = "GUEST";
    private static final String NOT_GUEST = "NOT_GUEST";
    private static final String NOT_USER = "NOT_USER";
    private static final String NOT_DOCTOR = "NOT_DOCTOR";
    private static final String NOT_ADMIN = "NOT_ADMIN";

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public int doStartTag() {
        HttpSession session = pageContext.getSession();
        Object sessionRole = session.getAttribute(SessionAttributes.ROLE);

        if (sessionRole == null) {
            if (GUEST.equalsIgnoreCase(this.role)
                    || NOT_USER.equalsIgnoreCase(role)
                    || NOT_DOCTOR.equalsIgnoreCase(role)) {
                return EVAL_BODY_INCLUDE;
            }
        } else if (sessionRole.toString().equalsIgnoreCase(this.role)
                || NOT_GUEST.equalsIgnoreCase(this.role)) {
            return EVAL_BODY_INCLUDE;
        } else if (NOT_USER.equalsIgnoreCase(role) && !sessionRole.equals("USER")) {
            return EVAL_BODY_INCLUDE;
        } else if (NOT_DOCTOR.equalsIgnoreCase(role) && !sessionRole.equals("DOCTOR")) {
            return EVAL_BODY_INCLUDE;
        } else if (NOT_ADMIN.equalsIgnoreCase(role) && !sessionRole.equals("ADMIN")) {
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
}
