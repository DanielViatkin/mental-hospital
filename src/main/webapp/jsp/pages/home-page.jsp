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
    <span class="h1 text-dark mx-auto mt-5">
        <fmt:message key="home-page.welcome"/>
    </span>
    <span class="h2 text-dark m-auto mt-5 pt-5">
        <fmt:message key="home-page.available.types"/>
    </span>

    <div id="carouselExampleIndicators" class="mt-5 carousel slide" data-ride="carousel">
        <ol class="carousel-indicators">
            <c:forEach items="${chamberTypes}" var="chamberType" varStatus="counter">
                <li data-target="#carouselExampleIndicators" data-slide-to="${counter}" class="
                    <c:if test="${counter.index == 0}">active</c:if>
                ">
                </li>
            </c:forEach>
        </ol>
        <div class="carousel-inner">
            <c:forEach items="${chamberTypes}" var="chamberType" varStatus="counter">
                <div class="carousel-item
                        <c:if test="${counter.index == 0}">active</c:if>
                    ">
                    <img class="d-block w-100" src="${chamberType.getImageRef()}" style="height: 60rem">
                    <div class="carousel-caption d-none d-md-block">
                        <h1>${chamberType.getName()}</h1>
                        <h2>Available chambers: ${chamberType.getNumberOfFreeRooms()}</h2>
                        <h2>Price: ${chamberType.getPrice()}</h2>
                        <h2>Beds: ${chamberType.getNumberOfBeds()}</h2>
                    </div>
                </div>
            </c:forEach>
        </div>
        <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

</body>
</html>