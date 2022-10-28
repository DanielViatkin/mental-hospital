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
    <form method="POST" action="${pageContext.request.contextPath}/MentalHospital?command=add-doctor"
          class="flex-box col-md-6 h2">
      <h1 class="text-center text-primary font-weight-bold"><fmt:message key="navbar.add.doctor"/></h1>

      <div class="mb-4">
        <span class="form-label"><fmt:message key="signup.email"/></span>
        <input type="email" name="login" minlength="5" maxlength="64" class="form-control mt-1"
               required style="font-size: 2rem">
      </div>
      <div class="mb-4">
        <span class="form-label"><fmt:message key="signup.pass"/></span>
        <input type="password" name="password" id="currentPass" minlength="8" maxlength="32"
               class="form-control password mt-1 mb-2" required style="font-size: 2rem">
        <input type="checkbox" onclick="showPass()"> <fmt:message key="settings.current.password.show"/>
      </div>
      <div class="mb-4">
        <span class="form-label"><fmt:message key="signup.first.name"/></span>
        <input type="text" name="firstName" minlength="4" maxlength="32" class="form-control mt-1" required
               style="font-size: 2rem">
      </div>
      <div class="mb-4">
        <span class="form-label"><fmt:message key="signup.last.name"/></span>
        <input type="text" name="lastName" minlength="4" maxlength="32" class="form-control mt-1" required
               style="font-size: 2rem">
      </div>

      <div class="mb-5">
        <span class="form-label"><fmt:message key="signup.phone.number"/></span>
        <input type="phone" pattern="[+]{1}[0-9]{12, 16}" name="phoneNumber" minlength="12" maxlength="18"
               class="form-control mt-1" style="font-size: 2rem">
      </div>

      <div class="mb-5">
        <span class="form-label"><fmt:message key="signup.classification"/></span>
        <input type="number" name="classification" min="1" max="10" class="form-control mt-1" required
               style="font-size: 2rem">
      </div>
      <div class="mb-5">
        <span class="form-label"><fmt:message key="signup.specialization"/></span>
        <input type="text" name="specialization" minlength="2" maxlength="40" class="form-control mt-1" required
               style="font-size: 2rem">
      </div>
      <div class="mb-5">
        <span class="form-label"><fmt:message key="signup.workExperience"/></span>
        <input type="number" name="workExperience" min="0" max="50" class="form-control mt-1" required
               style="font-size: 2rem">
      </div>
      <div class="mb-5">
        <span class="form-label"><fmt:message key="signup.price"/></span>
        <input type="number" name="price" min="5" max="500" class="form-control mt-1" required
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