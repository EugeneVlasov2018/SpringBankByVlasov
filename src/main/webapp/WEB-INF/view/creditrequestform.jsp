<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 21/09/2019
  Time: 02:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLang}"/>
<fmt:setBundle basename="pages/creditrequestform"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="setup.jsp"/>
</head>
<body>
<div class="container">
    <c:if test="${sessionScope.user.haveCreditAcc == true}">
        <fmt:message key="user.alerady.have.creditAcc"/>
    </c:if>
    <c:if test="${sessionScope.user.haveCreditAcc == false}">
        <fmt:message key="recomendation"/>
        <br>
        <fmt:message key="your.balance"/>
        <c:out value="${requestScope.summaryBalance}"/>
        <fmt:message key="after"/>
        <c:out value="${requestScope.afterHalfYearData}"/>
        <br>
        <form method="post">
            <input name="totalUserBalance" type="hidden" value="${requestScope.summaryBalance}">
            <input name="DataForCreditAcc" type="hidden" value="${requestScope.afterHalfYearData}">
            <label>
                <input name="creditLimit" placeholder="<fmt:message key="credit.limit"/>" type="text"
                       pattern="^[0-9]*[.,]?[0-9]+$">
            </label>
            <input type="submit" value="<fmt:message key="button.creditrequest"/>"
                   class="waves-effect waves-light btn"/>
        </form>
        <c:if test="${requestScope.zeropal==true}">
            <fmt:message key="pal.is.zero"/><br>
        </c:if>
        <br>
    </c:if>
</div>
</body>
</html>
