<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<div class="container-fluid d-flex justify-content-center flex-column align-items-center" style="text-decoration: none">
    <div class="w-50 d-flex flex-column mt-4 justify-content-center"
         style="background-color: #16CAEE; border-radius: 2rem">
        <div style="background-color: #4f7a9f; border-top-left-radius: 2rem; border-top-right-radius: 2rem;">
            <div class="d-flex text-light justify-content-between pt-2 px-3 h1">
                <span style="line-height: 1.5"><ftm:message
                        key="hospitalization.chamber.number"/> ${hospitalization.getChamberNumber()}</span>
                <span style="line-height: 1.5">${hospitalization.getChamberType().getName()}</span>
            </div>
        </div>
        <div class="container d-flex w-75 flex-column">
            <div class="d-flex py-3 pt-5 h2 justify-content-center">
                <span><fmt:message key="consultation.doctor"/></span>
                <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${hospitalization.getDoctorId()}&content=hospitalizations&content-size=5&current-page=1"
                   class="font-weight-bold">
                    <span class="ml-3">${hospitalization.getDoctorFirstName()} ${hospitalization.getDoctorLastName()}</span>
                </a>
            </div>
            <div class="d-flex py-3 h2 justify-content-center">
                <span><fmt:message key="consultation.patient"/></span>
                <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${hospitalization.getPatientId()}&content=hospitalizations&content-size=5&current-page=1"
                   class="font-weight-bold">
                    <span class="ml-3">${hospitalization.getPatientFirstName()} ${hospitalization.getPatientLastName()}</span>
                </a>
            </div>
            <div class="d-flex py-3 h2 justify-content-center">
                <span><fmt:message key="hospitalization.date.in"/></span>
                <span class="ml-3">${hospitalization.getDateIn()}</span>
            </div>
            <c:choose>
                <c:when test="${hospitalization.getStatus().toString().equals('COMPLETED')}">
                    <div class="d-flex py-3 h2 justify-content-center">
                        <span><fmt:message key="hospitalization.date.out"/></span>
                        <span class="ml-3">${hospitalization.getDateOut()}</span>
                    </div>
                    <div class="d-flex py-3 pb-4 h2 justify-content-center">
                        <span><fmt:message key="hospitalization.price"/></span>
                        <span class="ml-3">${hospitalization.getPrice()} <fmt:message key="units.dollar"/></span>
                    </div>
                </c:when>
            </c:choose>
        </div>
        <c:choose>
            <c:when test="${hospitalization.getStatus().toString().equals('REJECTED')}">
                <div class="d-flex justify-content-center h3 bg-danger mb-0"
                     style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                    <span class="py-3 text-center text-white h1 mb-0"
                          style="line-height: 1.5">${hospitalization.getStatus().toString()}</span>
                </div>
            </c:when>
            <c:otherwise>
                <div class="d-flex justify-content-center h3 bg-success mb-0"
                     style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                    <span class="py-3 text-center text-white h1 mb-0"
                          style="line-height: 1.5">${hospitalization.getStatus().toString()}</span>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

    <sc:access role="DOCTOR">
        <c:if test="${hospitalization.getStatus().toString().equals('PENDING')}">
            <form class="d-flex align-items-center w-50 justify-content-around mt-4" method="POST"
                  action="${pageContext.request.contextPath}/MentalHospital?id=${hospitalizationId}">
                <button type="submit" name="command" value="hospitalization-approve"
                        class="btn btn-success btn-lg d-flex mx-auto justify-content-center">
                                    <span class="h1 mb-0" style="line-height: 1.6; width: 15rem"><fmt:message
                                            key="hospitalization.approve.button"/></span>
                </button>
                <button type="submit" name="command" value="hospitalization-reject"
                        class="btn btn-danger btn-sm d-flex mx-auto justify-content-center">
                                    <span class="h1 mb-0" style="line-height: 1.6; width: 15rem"><fmt:message
                                            key="hospitalization.reject.button"/></span>
                </button>
            </form>
        </c:if>
        <c:if test="${hospitalization.getStatus().toString().equals('APPROVED')}">
            <span class="mt-4 h1 glyphicon glyphicon-arrow-down"></span>

            <form class="bg-info d-flex flex-column flex-box col-md-6 w-50 border border-dark mt-4 row align-items-center mb-5"
                  method="POST"
                  style="width: 55%; border-radius: 6rem"
                  action="${pageContext.request.contextPath}/MentalHospital?id=${hospitalizationId}">
                <span class="h1 pb-3 pt-4 text-center font-weight-bold"><fmt:message
                        key="hospitalization.course"/></span>
                <div class="py-3 pt-3 h2 w-75 mb-3" style="margin-left: 3rem">
                    <span class="form-label"><fmt:message key="hospitalization.date.out"/></span>
                    <input class="date mt-1 form-control"
                           id="dateOut"
                           type="datetime-local"
                           max="2022-12-31T23:59"
                           placeholder="<fmt:message key="hospitalization.date.advice"/>"
                           name="dateOut" value="${dateOut}" style="font-size: 2rem" required>
                </div>
                <div class="py-3 pt-3 h2 w-75 mb-3 ml-5">
                    <span class="form-label"><fmt:message key="hospitalization.price"/></span>
                    <input name="price" class="form-control mt-1" type="number" min="10" max="10000"
                           style="font-size: 2rem" required>
                </div>

                <div class="d-flex w-75 justify-content-between mb-4">
                    <button type="submit" name="command" value="hospitalization-complete"
                            class="btn btn-success btn-lg d-flex mx-auto justify-content-center">
                            <span class="h2 mb-0" style="line-height: 2"><fmt:message
                                    key="hospitalization.complete.button"/></span>
                    </button>
                </div>

            </form>
        </c:if>
    </sc:access>
</div>
<script src="/js/complete-consultation-dynamic-table.js"></script>
</body>
</html>