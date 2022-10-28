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
        <form method="POST" action="${pageContext.request.contextPath}/MentalHospital?command=add-drug"
              class="flex-box col-md-6 h2">
            <h1 class="text-center text-primary font-weight-bold"><fmt:message key="navbar.add.drug"/></h1>
            <div class="mb-4">
                <span class="form-label"><fmt:message key="drug.add.name"/></span>
                <input type="text" name="name" minlength="4" maxlength="32" class="form-control mt-1" required
                       style="font-size: 2rem">
            </div>

            <h3 class="text-danger error-message mb-4">
                <fmt:message key="consultation.request.error.${errorMessage}"/>
            </h3>
            <button type="submit" name="add"
                    class="btn btn-primary w-25 btn-lg d-flex mx-auto justify-content-center" style="width: 20rem">
                <span class="h2 mb-0" style="line-height: 1.6"><fmt:message key="add.button"/></span>
            </button>
        </form>
    </div>
</div>
</body>
</html>