<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <c:import url="/jsp/elements/head.jsp"/>
    <title><fmt:message key="label.title"/></title>
</head>
<body>

<body>
<c:import url="/jsp/elements/navbar.jsp"/>

<div class="container py-3">
    <div class="row flex-column align-items-center">
        <form method="POST" action="${pageContext.request.contextPath}/MentalHospital?command=consultation-request"
              class="flex-box col-md-6 h2">
            <h1 class="text-center text-primary font-weight-bold"><fmt:message key="consultation.request"/></h1>
            <div class="mb-4">
                <span class="form-label"><fmt:message key="consultation.date"/></span>
                <input class="date mt-1 form-control"
                       id="date"
                       type="datetime-local"
                       max="2022-12-31T23:59"
                       placeholder="<fmt:message key="consultation.date.advice"/>"
                       name="date" value="${date}" style="font-size: 2rem" required>
            </div>

            <div class="mb-4">
                <span class="form-label"><fmt:message key="consultation.doctor.label"/></span>
                <select name="doctor" class="form-select form-control form-select-lg mt-1"
                        aria-label=".form-select-lg example" style="font-size: 2rem; height: 5rem">
                    <option selected><fmt:message key="consultation.doctor.select"/></option>
                    <c:forEach items="${doctors}" var="doctor" varStatus="counter">
                        <option value="${doctor.getFirstName().concat(" ").concat(doctor.getLastName())}">${doctor.getFirstName().concat(" ").concat(doctor.getLastName())}</option>
                    </c:forEach>
                </select>
            </div>


            <div class="form-check form-switch mb-5">
                <input class="form-check-input" name="isOnline" type="checkbox" id="flexSwitchCheckDefault"
                       style="width: 2rem; height: 2rem">
                <label class="form-check-label ml-4" for="flexSwitchCheckDefault"><fmt:message
                        key="consultation.online"/></label>
            </div>

            <h3 class="text-danger error-message mb-4">
                <fmt:message key="consultation.request.error.${errorMessage}"/>
            </h3>
            <button type="submit" name="Log in"
                    class="btn btn-primary w-25 btn-lg d-flex mx-auto justify-content-center" style="width: 20rem">
                <span class="h2 mb-0" style="line-height: 1.6"><fmt:message key="consultation.request.button"/></span>
            </button>
        </form>
    </div>
</div>
<script src="/js/set-min-date.js"></script>
</body>
</html>