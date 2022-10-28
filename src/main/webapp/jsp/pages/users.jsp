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
<c:import url="/jsp/elements/navbar.jsp"/>

<div class="container py-5 mt-2">
    <div class="table-responsive">
        <div class="table-wrapper">

            <table class="table table-striped table-hover h3">
                <thead>
                <tr>
                    <%--                    <th><fmt:message key="user.login"/></th>--%>
                    <%--                    <th><fmt:message key="user.data.time"/></th>--%>
                    <%--                    <th><fmt:message key="user.role"/></th>--%>
                    <%--                    <th><fmt:message key="user.status"/></th>  --%>
                    <th>#</th>
                    <th>Full Name</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Status</th>
                    <th>Action</th>
                </tr>
                </thead>
                <tbody class="table-of-users h3">
                <c:forEach items="${users}" var="user" varStatus="counter">
                    <tr class="py-2">
                        <td>${user.getId()}</td>
                        <td>
                            <a href="${pageContext.request.contextPath}/MentalHospital?command=profile-page&id=${user.getId()}&content=consultations&content-size=5&current-page=1">
                                    ${user.getFullName()}
                            </a>
                        </td>
                        <td>${user.getEmail()}</td>
                        <td>${user.getRole()}</td>
                        <td id="status-part">
                            <div>
                                       <span id="status-dot" class="status
<c:choose>
<c:when test="${user.getStatus() == 'ACTIVE'}">text-success</c:when><c:otherwise>text-danger</c:otherwise>
</c:choose> mr-2 h2">•</span>
                                <span class="status-user">${user.getStatus()}</span>
                            </div>
                        </td>
                        <td>
                            <form method="POST" class="d-flex justify-content-between"
                                  action="${pageContext.request.contextPath}/MentalHospital?id=${user.getId()}">
                                <c:choose>
                                    <c:when test="${user.getStatus().toString().equals('ACTIVE')}">
                                        <button type="submit" class="btn border btn-action-ban mr-5
                                                btn-outline-danger"
                                                name="command" value="ban" style="width: 10rem;">
                                            <span class="h2">BAN</span>
                                        </button>
                                    </c:when>
                                    <c:otherwise>
                                        <button type="submit" class="btn border btn-action-ban
                                                btn-outline-success mr-5"
                                                name="command" value="unban" style="width: 10rem;">
                                            <span class="h2">UNBAN</span>
                                        </button>
                                    </c:otherwise>
                                </c:choose>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>
