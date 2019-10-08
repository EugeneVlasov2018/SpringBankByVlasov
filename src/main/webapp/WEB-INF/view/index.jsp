<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 12/09/2019
  Time: 20:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<c:if test="${not empty sessionScope.currentLang}">
    <fmt:setLocale value="${sessionScope.currentLang}" scope="session"/>
</c:if>
<c:if test="${empty sessionScope.currentLang}">
    <fmt:setLocale value="en_EN" scope="session"/>
</c:if>
<fmt:setBundle basename="pages/index"/>

<html>
<head>
    <title>Title</title>
</head>
<body>
<div align="center">
    <fmt:message key="main.message.index"/>
</div>
<br>
<br>
<fmt:message key="choose.language"/><br>
<form action="${pageContext.request.contextPath}/changelang" method="post">
    <label>
        <select name="language">
            <option value="ru_RU">русский</option>
            <option value="en_EN">english</option>
        </select>
        <input type="submit" value="<fmt:message key="lang.button"/>"/>
    </label>
</form>
<br>
<div align="center">
    <form method="post" action="${pageContext.request.contextPath}">
        <label>
            <input hidden name="command" value="loginUser">
        </label>

        <label>
            <input name="login" type="text" placeholder="<fmt:message key="users.login"/>" required>
        </label>
        <br>
        <label>
            <input name="password" type="password" placeholder="<fmt:message key="users.password"/>" required>
        </label>
        <div>
            <c:if test="${requestScope.wrongParameter==true}">
                <fmt:message key="wrong.password"/><br>
            </c:if>
        </div>

        <input type="submit" value="<fmt:message key="login.button"/>"><br>
    </form>
</div>
<br>
</body>
</html>