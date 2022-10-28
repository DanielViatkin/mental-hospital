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

<c:import url="/jsp/elements/navbar.jsp"/>

<div class="container-fluid py-3" style="width: 80%">
    <div class="d-flex justify-content-center">
        <div class="d-flex flex-column align-items-center rounded text-center mt-5 p-3 mr-5"
             style="background-color: #16CAEE;height: 100%;">
            <c:choose>
                <c:when test="${userInfo.getRole().toString().equals('DOCTOR')}">
                    <img src="https://static.zerochan.net/Miyaura.Sanshio.full.1963705.jpg" alt="notPhoto"
                         class="rounded-circle" width="260">
                </c:when>
                <c:when test="${userInfo.getRole().toString().equals('USER')}">
                    <img src="https://i1.sndcdn.com/artworks-000457003131-wkr02i-t500x500.jpg" alt="notPhoto"
                         class="rounded-circle" width="260">
                </c:when>
            </c:choose>

            <div class="mt-4 h2">
                <h4 class="h1">${userInfo.getFullName()}</h4>
                <p class="text-muted mt-3 h2">${userInfo.getRole()}</p>
                <p class="mt-3 h2
                    <c:choose>
                    <c:when test="${userInfo.getStatus() == 'ACTIVE'}">text-success</c:when><c:otherwise>text-danger</c:otherwise>
                    </c:choose>">
                    ${userInfo.getStatus()}
                </p>
                <p>
                    <c:if test="${content.equals('consultations')}">
                        <a class="btn btn-primary btn-lg mt-4"
                           href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${profileId}&content=hospitalizations&content-size=5&current-page=1"
                           role="button">
                        <span class="h3">
                            <fmt:message key="profile.show.hospitalizations"/>
                        </span>
                        </a>
                    </c:if>
                    <c:if test="${content.equals('hospitalizations')}">
                        <a class="btn btn-primary btn-lg mt-4"
                           href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${profileId}&content=consultations&content-size=5&current-page=1"
                           role="button">
                        <span class="h3">
                            <fmt:message key="profile.show.consultations"/>
                        </span>
                        </a>
                    </c:if>
                </p>
                <a class="btn btn-primary btn-lg mt-4" data-toggle="collapse" href="#collapse" role="button"
                   aria-expanded="false" aria-controls="collapseExample">
                    <span class="h3">
                        <fmt:message key="profile.additional.info"/>
                    </span>
                </a>
                <div class="collapse" id="collapse">
                    <c:choose>
                        <c:when test="${userInfo.getRole().toString().equals('DOCTOR')}">
                            <p class="mt-3 h3">
                                <fmt:message key="profile.specialization"/> ${doctorInfo.getSpecialization()}
                            </p>
                            <p class="mt-3 h3">
                                <fmt:message key="profile.classification"/> ${doctorInfo.getClassification()}
                            </p>
                            <p class="mt-3 h3">
                                <fmt:message key="profile.work-experience"/> ${doctorInfo.getWorkExperience()}
                            </p>
                            <p class="mt-3 h3">
                                <fmt:message key="profile.price"/> ${doctorInfo.getPrice()}
                            </p>
                        </c:when>
                        <c:otherwise>
                            <p class="mt-3 h3">
                                <fmt:message key="profile.age"/> ${patientInfo.getAge()}
                            </p>
                            <p class="mt-3 h3">
                                <fmt:message key="profile.spare-number"/> ${patientInfo.getSpareNumber()}
                            </p>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <c:if test="${(userInfo.getRole().equals('DOCTOR') &&
                        userInfo.getId().equals(sessionScope.userId)) ||
                        sessionScope.role == 'ADMIN' ||
                        (userInfo.getRole() == 'USER' &&
                        (userInfo.getId().equals(sessionScope.userId) ||
                        sessionScope.role.equals('DOCTOR')))}">
            <c:if test="${content.equals('consultations')}">
                <div class="d-flex flex-column w-50 mt-5 ml-5 align-items-center text-center">
                    <span class="h1 text-primary mb-4 font-weight-bold"><fmt:message key="consultations.label"/></span>
                    <c:forEach items="${consultations}" var="consultation" varStatus="counter">
                        <a href="${pageContext.request.contextPath}/MentalHospital?command=consultation&id=${consultation.getId()}"
                           class="text-center w-75 mb-5"
                           style="background-color: #16CAEE; border-radius: 2rem; text-decoration: none">
                            <div class="d-flex text-light justify-content-between pt-2 px-3 h2"
                                 style="background-color: #4f7a9f; border-top-left-radius: 2rem; border-top-right-radius: 2rem;">
                                <span style="line-height: 1.5"><sc:date-formatter date="${consultation.getDate()}"
                                                                                  formatType="${sessionScope.lang}"></sc:date-formatter></span>
                                <span style="line-height: 1.5">${consultation.getCommunicationType()}</span>
                            </div>
                            <span class="h2 mt-2 mb-2 font-weight-bold" style="line-height: 3; color: #428bca">
                                  <c:choose>
                                      <c:when test="${userInfo.getRole().toString().equals('DOCTOR')}">
                                          <fmt:message key="consultation.patient"/> ${consultation.getPatientFullName()}
                                      </c:when>
                                      <c:otherwise>
                                          <fmt:message key="consultation.doctor"/> ${consultation.getDoctorFullName()}
                                      </c:otherwise>
                                  </c:choose>
                            </span>
                            <c:choose>
                                <c:when test="${consultation.getConsultationStatus().toString().equals('REJECTED')}">
                                    <div class="d-flex justify-content-center bg-danger mb-0"
                                         style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                                     <span class="py-3 text-center text-white h3 mb-0">
                                             ${consultation.getConsultationStatus().toString()}
                                     </span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="d-flex justify-content-center bg-success mb-0"
                                         style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                                    <span class="py-3 text-center text-white h3 mb-0">
                                            ${consultation.getConsultationStatus().toString()}
                                    </span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </c:forEach>
                    <div class="pagination mb-3">
                        <c:if test="${currentPage != 1}">
                            <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${profileId}&content=consultations&content-size=5&current-page=${currentPage-1}">&#8592;</a>
                        </c:if>
                        <c:if test="${fullContentSize > 0}">
                            <h3 class="px-2 my-1">${currentPage}</h3>
                        </c:if>

                        <c:if test="${currentPage * contentSize < fullContentSize}">
                            <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${profileId}&content=consultations&content-size=5&current-page=${currentPage+1}">&#8594;</a>
                        </c:if>
                    </div>
                </div>
            </c:if>
            <c:if test="${content.equals('hospitalizations')}">
                <div class="d-flex flex-column w-50 mt-5 ml-5 align-items-center text-end">
                    <span class="h1 text-primary mb-4 font-weight-bold"><fmt:message
                            key="hospitalization.label"/></span>
                    <c:forEach items="${hospitalizations}" var="hospitalization" varStatus="counter">
                        <a href="${pageContext.request.contextPath}/MentalHospital?command=hospitalization&id=${hospitalization.getId()}"
                           class="text-end w-75 mb-5"
                           style="background-color: #16CAEE; border-radius: 2rem; text-decoration: none">
                            <div class="d-flex text-light justify-content-between pt-2 px-3 h2"
                                 style="background-color: #4f7a9f; border-top-left-radius: 2rem; border-top-right-radius: 2rem;">
                                <span style="line-height: 1.5">
                                    <sc:date-formatter date="${hospitalization.getDate()}"
                                                       formatType="${sessionScope.lang}"></sc:date-formatter>
                                </span>
                                <span style="line-height: 1.5"><fmt:message
                                        key="hospitalization.chamber.number"/> ${hospitalization.getChamberNumber()} </span>
                            </div>
                            <span class="h2 mt-2 mb-2 font-weight-bold d-flex justify-content-center"
                                  style="line-height: 3; color: #428bca">
                                  <c:choose>
                                      <c:when test="${userInfo.getRole().toString().equals('DOCTOR')}">
                                          <fmt:message
                                                  key="consultation.patient"/> ${hospitalization.getPatientFullName()}
                                      </c:when>
                                      <c:otherwise>
                                          <fmt:message
                                                  key="consultation.doctor"/> ${hospitalization.getDoctorFullName()}
                                      </c:otherwise>
                                  </c:choose>
                            </span>
                            <c:choose>
                                <c:when test="${hospitalization.getHospitalizationStatus().toString().equals('REJECTED')}">
                                    <div class="d-flex justify-content-center bg-danger mb-0"
                                         style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                                     <span class="py-3 text-center text-white h3 mb-0">
                                             ${hospitalization.getHospitalizationStatus().toString()}
                                     </span>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="d-flex justify-content-center bg-success mb-0"
                                         style="border-bottom-left-radius: 2rem; border-bottom-right-radius: 2rem;">
                                    <span class="py-3 text-center text-white h3 mb-0">
                                            ${hospitalization.getHospitalizationStatus().toString()}
                                    </span>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </a>
                    </c:forEach>
                    <div class="pagination mb-3">
                        <c:if test="${currentPage != 1}">
                            <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${profileId}&content=hospitalizations&content-size=5&current-page=${currentPage-1}">&#8592;</a>
                        </c:if>
                        <c:if test="${fullContentSize > 0}">
                            <h3 class="px-2 my-1">${currentPage}</h3>
                        </c:if>
                        <c:if test="${currentPage * contentSize < fullContentSize}">
                            <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${profileId}&content=hospitalizations&content-size=5&current-page=${currentPage+1}">&#8594;</a>
                        </c:if>
                    </div>
                </div>
            </c:if>
        </c:if>
    </div>
</div>

</div>
</body>

</body>
</html>