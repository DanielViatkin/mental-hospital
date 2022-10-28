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

<div class="container d-flex flex-column h3">
    <div class="d-flex flex-column w-100 mt-5 py-5 ml-5 align-items-center text-center">
        <div class="text-center mb-5"
             style="background-color: #16CAEE; border-radius: 1.5rem; text-decoration: none; width:50%">
            <div class="d-flex text-light justify-content-center pt-2 px-3 h2"
                 style="background-color: #4f7a9f; border-top-left-radius: 2rem; border-top-right-radius: 2rem;">
                <span style="line-height: 1.5">${disease.getName()}</span>
            </div>
            <span class="h3 mt-2 mb-2 font-weight-bold p-2" style="line-height: 1.8; color: #428bca">
                ${disease.getDescription()}
            </span>
        </div>
    </div>
</div>

</body>
</html>