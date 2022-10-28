<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sc" uri="custom-tags" %>

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

<div class="container-fluid d-flex justify-content-center flex-column align-items-center mb-3" style="text-decoration: none">
    <c:forEach items="${consultations}" var="consultation" varStatus="consultationCounter">
        <div class="w-50 d-flex flex-column mt-4 justify-content-center"
             style="background-color: #16CAEE; border-radius: 2rem">
            <div style="background-color: #4f7a9f; border-top-left-radius: 2rem; border-top-right-radius: 2rem;">
                <div class="d-flex text-light justify-content-between pt-2 px-3 h1">
            <span style="line-height: 1.5"><sc:date-formatter date="${consultation.getDate()}"
                                                              formatType="${sessionScope.lang}"></sc:date-formatter></span>
                    <span style="line-height: 1.5">${consultation.getCommunicationType()}</span>
                </div>
            </div>
            <div class="container d-flex w-75 flex-column">
                <div class="d-flex py-3 pt-5 h2 justify-content-center">
                    <span><fmt:message key="consultation.doctor"/></span>
                    <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${consultation.getDoctorId()}&content=consultations&content-size=5&current-page=1"
                       class="font-weight-bold">
                        <span class="ml-3">${consultation.getDoctorFirstName()} ${consultation.getDoctorLastName()}</span>
                    </a>
                </div>
                <div class="d-flex py-3 h2 justify-content-center">
                    <span><fmt:message key="consultation.patient"/></span>
                    <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${consultation.getPatientId()}&content=consultations&content-size=5&current-page=1"
                       class="font-weight-bold">
                        <span class="ml-3">${consultation.getPatientFirstName()} ${consultation.getPatientLastName()}</span>
                    </a>
                </div>
                <c:choose>
                    <c:when test="${consultation.getConsultationStatus().toString().equals('COMPLETED')}">
                        <c:if test="${consultation.isCourseExist()}">
                            <div class="d-flex py-3 pb-4 h2 justify-content-center">
                                <span><fmt:message key="consultation.duration"/></span>
                                <span class="ml-3">${consultation.getDuration()} <fmt:message key="units.minutes"/></span>
                            </div>
                        </c:if>
                        <div class="d-flex py-3 pb-4 h2 justify-content-center">
                            <span><fmt:message key="consultation.price"/></span>
                            <span class="ml-3">${consultation.getPrice()}</span>
                            <span class="glyphicon glyphicon-usd" style="margin-top: -1px"></span>
                            <span class="ml-2"> <c:if test="${!consultation.isCourseExist()}"> (50% off)</c:if></span>
                        </div>
                    </c:when>
                </c:choose>
            </div>
            <c:choose>
                <c:when test="${consultation.getConsultationStatus().toString().equals('REJECTED')}">
                    <div class="d-flex justify-content-center h3 bg-danger mb-0"
                         style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                    <span class="py-3 text-center text-white h1 mb-0"
                          style="line-height: 1.5">${consultation.getConsultationStatus().toString()}</span>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="d-flex justify-content-center h3 bg-success mb-0"
                         style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                    <span class="py-3 text-center text-white h1 mb-0"
                          style="line-height: 1.5">${consultation.getConsultationStatus().toString()}</span>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>

        <c:if test="${consultation.getConsultationStatus().toString().equals('COMPLETED') && consultation.isCourseExist()}">
            <span class="mt-4 h1 glyphicon glyphicon-arrow-down"></span>

            <div class="bg-info d-flex flex-column w-50  border  border-dark mt-4"
                 style="width: 55%; border-radius: 6rem">
                <span class="h1 pb-3 pt-4 text-center font-weight-bold"><fmt:message key="consultation.course"/></span>
                <div class="d-flex py-3 pt-3 h2" style="margin-left: 3rem">
                    <span><fmt:message key="consultation.instructions"/></span>
                    <span class="ml-2">${consultation.getInstruction()}</span>
                </div>
                <div class="d-flex py-3 h2" style="margin-left: 3rem">
                    <div class="d-flex flex-column">
                        <span class="mb-2"><fmt:message key="consultation.symptoms-with-diseases"/></span>
                        <c:forEach items="${consultation.getDiseases()}" var="disease" varStatus="counter">
                            <div class="mb-1">
                                <span class="glyphicon glyphicon-pushpin mr-3 ml-4"></span>
                                <span>${disease.getSymptoms()} </span>
                                <span class="glyphicon glyphicon-hand-right mx-3 h3"
                                      style="margin-top: 1px; margin-left: 1px"></span>
                                <a href="${pageContext.request.contextPath}/MentalHospital?command=disease&id=${disease.getId()}"
                                   class="text-dark font-weight-bold">
                                        ${disease.getName()}
                                </a>
                            </div>
                        </c:forEach>
                    </div>
                </div>
                <div class="d-flex py-3 pb-4 h2" style="margin-left: 3rem">
                    <div class="d-flex flex-column">
                        <span class="mb-2"><fmt:message key="consultation.drug.recipes"/></span>
                        <c:forEach items="${consultation.getDrugs()}" var="drug" varStatus="counter">
                            <div class="mb-1">
                                <span class="glyphicon glyphicon-hand-right mx-3 ml-4"></span>
                                <a href="https://www.google.by/search?q=${drug.getName()}+drug"
                                   class="text-dark font-weight-bold">
                                        ${drug.getName()}
                                </a>
                                <span>(${drug.getDoze()} mg)</span>
                                <span class="glyphicon glyphicon-minus h2"></span>
                                <span>${drug.getDescription()}</span>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>


            <sc:access role="USER">
                <c:if test="${consultationCounter.index >= consultations.size() - 1}">
                    <span class="mt-4 h1 glyphicon glyphicon-arrow-down"></span>
                    <form class="d-flex w-75 row flex-column align-items-center mb-4 mt-4" method="POST"
                          action="${pageContext.request.contextPath}/MentalHospital?command=consultation-request&parent_id=${consultationId}">
                        <div class="p-4" style="border-radius: 3rem;background-color: #16CAEE;">
                            <div class="form-check form-switch mb-4">
                                <input class="form-check-input" name="isOnline" type="checkbox" id="flexSwitchCheckDefault"
                                       style="width: 1.7rem; height: 2rem">
                                <label class="form-check-label ml-4 mt-1" for="flexSwitchCheckDefault"><fmt:message
                                        key="consultation.online"/></label>
                            </div>
                            <div class="mb-4">
                                <span class="form-label"><fmt:message key="consultation.date"/></span>
                                <input class="date mt-1 form-control"
                                       id="date"
                                       type="datetime-local"
                                       max="2022-12-31T23:59"
                                       placeholder="<fmt:message key="consultation.date.advice"/>"
                                       name="date" value="${date}" style="font-size: 1.7rem" required>
                            </div>
                            <button type="submit"
                                    class="btn btn-success btn-lg d-flex mx-auto justify-content-center">
                            <span class="h3 mb-0" style="line-height: 1.5">
                                <fmt:message key="consultation.repeat.request.button"/>
                            </span>
                            </button>
                        </div>
                    </form>
                </c:if>
            </sc:access>
        </c:if>

        <sc:access role="DOCTOR">
            <c:if test="${consultation.getConsultationStatus().toString().equals('PENDING')}">
                <form class="d-flex align-items-center w-50 justify-content-around mt-4" method="POST"
                      action="${pageContext.request.contextPath}/MentalHospital?id=${consultationId}">
                    <button type="submit" name="command" value="consultation-approve"
                            class="btn btn-success btn-lg d-flex mx-auto justify-content-center">
                                    <span class="h1 mb-0" style="line-height: 1.6; width: 15rem"><fmt:message
                                            key="consultation.approve.button"/></span>
                    </button>
                    <button type="submit" name="command" value="consultation-reject"
                            class="btn btn-danger btn-sm d-flex mx-auto justify-content-center">
                                    <span class="h1 mb-0" style="line-height: 1.6; width: 15rem"><fmt:message
                                            key="consultation.reject.button"/></span>
                    </button>
                </form>
            </c:if>
            <c:if test="${consultation.getConsultationStatus().toString().equals('APPROVED')}">
                <span class="mt-4 h1 glyphicon glyphicon-arrow-down"></span>

                <form class="bg-info d-flex flex-column flex-box col-md-6 w-50 border border-dark mt-4 row align-items-center mb-5"
                      method="POST"
                      style="width: 55%; border-radius: 6rem"
                      action="${pageContext.request.contextPath}/MentalHospital?id=${consultationId}">
                    <span class="h1 pb-3 pt-4 text-center font-weight-bold"><fmt:message
                            key="consultation.course"/></span>
                    <div class="py-3 pt-3 h2 w-75 mb-3" style="margin-left: 3rem">
                        <span class="form-label"><fmt:message key="consultation.instructions.label"/></span>
                        <input type="text" name="instructions" minlength="1" maxlength="320" class="form-control mt-1"
                               required
                               style="font-size: 2rem">
                    </div>
                    <div class="py-3 pt-3 h2 w-75 mb-3" style="margin-left: 3rem">
                        <span class="form-label"><fmt:message key="consultation.duration.label"/></span>
                        <input name="duration" class="form-control mt-1" type="number" min="10" max="180"
                               style="font-size: 2rem" required>
                    </div>

                    <div class="col-md-12 w-75 column container mb-3">
                        <table class="table table-bordered table-hover mb-0" id="tab_logic">
                            <thead>
                            <tr class="h2">
                                <th class="text-center">
                                    Patient symptoms
                                </th>
                                <th class="text-center">
                                    Patient disease
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr id='diseaseSymptom0'>
                                <td>
                                    <input type="text" name='symptoms-0' placeholder='Enter patient symptoms'
                                           class="form-control mx-auto" style="font-size: 1.5rem; width: 82%" required/>
                                </td>
                                <td>
                                    <input type="text" name='disease-0' placeholder='Enter patient disease'
                                           class="form-control mx-auto" style="font-size: 1.5rem; width: 82%" required/>
                                </td>
                            </tr>
                            <tr id='diseaseSymptom1'></tr>
                            </tbody>
                        </table>
                        <a id="add_row" class="btn btn-success">
                            <span class="glyphicon glyphicon-plus text-white"></span>
                        </a>
                        <a id='delete_row' class="btn btn-danger">
                            <span class="glyphicon glyphicon-minus text-white"></span>
                        </a>
                    </div>

                    <div class="col-md-12 w-75 column container mb-5">
                        <table class="table table-bordered table-hover mb-0" id="tab_logic_1">
                            <thead>
                            <tr class="h2">
                                <th class="text-center">
                                    Drug
                                </th>
                                <th class="text-center">
                                    Dose
                                </th>
                                <th class="text-center">
                                    Description
                                </th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr id='drugRecipe0'>
                                <td>
                                    <input type="text" name='drug-0' placeholder='Enter drug'
                                           class="form-control mx-auto"
                                           style="font-size: 1.5rem; width: 82%" required/>
                                </td>
                                <td>
                                    <input type="text" name='dose-0' placeholder='Enter dose'
                                           class="form-control mx-auto"
                                           style="font-size: 1.5rem; width: 82%" required/>
                                </td>
                                <td>
                                    <input type="text" name='description-0' placeholder='Enter description'
                                           class="form-control mx-auto" style="font-size: 1.5rem; width: 82%"/>
                                </td>
                            </tr>
                            <tr id='drugRecipe1'></tr>
                            </tbody>
                        </table>
                        <a id="add_row_1" class="btn btn-success">
                            <span class="glyphicon glyphicon-plus text-white"></span>
                        </a>
                        <a id='delete_row_1' class="btn btn-danger">
                            <span class="glyphicon glyphicon-minus text-white"></span>
                        </a>
                    </div>

                    <div class="d-flex w-75 justify-content-between mb-4">
                        <button type="submit" name="command" value="consultation-complete"
                                class="btn btn-success btn-lg d-flex mx-auto justify-content-center">
                            <span class="h3 mb-0" style="line-height: 1.5"><fmt:message
                                    key="consultation.complete.button"/></span>
                        </button>
                        <button type="submit" name="command" value="consultation-reject"
                                class="btn btn-danger btn-lg d-flex mx-auto justify-content-center">
                            <span class="h3 mb-0" style="line-height: 1.5"><fmt:message
                                    key="consultation.reject.button"/></span>
                        </button>
                    </div>

                </form>
                <form class="d-flex w-50 mb-4"
                      method="POST"
                      style="width: 55%; border-radius: 6rem"
                      action="${pageContext.request.contextPath}/MentalHospital?command=consultation-complete-without-course&id=${consultationId}">>
                    <button type="submit" class="btn btn-primary btn-sm d-flex mx-auto justify-content-center">
                            <span class="h3 mb-0" style="line-height: 1.5"><fmt:message
                                    key="consultation.complete-without-course.button"/></span>
                    </button>
                </form>

            </c:if>
        </sc:access>

        <c:if test="${consultationCounter.index < consultations.size() - 1}">
            <span class="mt-4 mb-1 h1 glyphicon glyphicon-arrow-down"></span>
        </c:if>

    </c:forEach>
</div>
<script src="/js/complete-consultation-dynamic-table.js"></script>
</body>
</html>