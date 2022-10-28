<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="locale"/>

<html lang="<fmt:message key="html.lang"/>">
<head>
    <c:import url="/jsp/elements/head.jsp"/>
    <title><fmt:message key="label.title"/></title>
</head>
<body>


<c:import url="/jsp/elements/navbar.jsp"/>

<div class="container py-3">
    <div class="row flex-column align-items-center">
        <form name="signupForm" method="POST" action="${pageContext.request.contextPath}/MentalHospital?command=sign-up"
              class="flex-box col-md-6 h2">
            <h1 class="text-center text-primary font-weight-bold"><fmt:message key="signup.reg"/></h1>
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
                <span class="form-label"><fmt:message key="signup.repeat.pass"/></span>
                <input type="password" class="form-control password mt-1" minlength="8" maxlength="32" required
                       style="font-size: 2rem">
                <span class="password-error d-none text-danger"><fmt:message key="signup.repeat.pass.error"/></span>
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
            <select name="sex" class="form-select form-control form-select-lg mt-1 mb-4"
                    aria-label=".form-select-lg example" style="font-size: 2rem; height: 5rem">
                <option selected><fmt:message key="signup.sex.click"/></option>
                <option value="male">
                    MALE
                </option>
                <option value="female">
                    FEMALE
                </option>
            </select>

            <div class="mb-4">
                <span class="form-label"><fmt:message key="signup.age"/></span>
                <input type="number" name="age" min="18" max="150" class="form-control mt-1" required
                       style="font-size: 2rem">
            </div>
            <div class="mb-5">
                <span class="form-label"><fmt:message key="signup.spare.phone.number"/></span>
                <input type="phone" pattern="[+]{1}[0-9]{12, 16}" name="sparePhoneNumber" minlength="12" maxlength="18"
                       class="form-control mt-1" style="font-size: 2rem">
            </div>
            <h3 class="text-danger error-message mb-4">
                <fmt:message key="login.error.${errorMessage}"/>
            </h3>
            <button type="submit" name="Log in"
                    class="btn btn-primary w-25 btn-lg d-flex mx-auto justify-content-center" style="width: 20rem">
                <span class="h2 mb-0" style="line-height: 1.6"><fmt:message key="signup.btn.submit"/></span>
            </button>
        </form>
    </div>
</div>
<script src="/js/checker-repeat-pas.js"></script>
</body>
</html>
