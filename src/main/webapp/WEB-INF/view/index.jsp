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

<!-- Compiled and minified CSS -->

<c:if test="${not empty sessionScope.currentLang}">
    <fmt:setLocale value="${sessionScope.currentLang}" scope="session"/>
</c:if>
<c:if test="${empty sessionScope.currentLang}">
    <fmt:setLocale value="en_US" scope="session"/>
</c:if>
<fmt:setBundle basename="pages/index"/>

<html>
<head>
    <title>Title</title>
    <jsp:include page="setup.jsp"/>
</head>
<body>
<div class="container">
    <div class="center-align">
    <fmt:message key="main.message.index"/>
</div>
<br>
<br>

    <div class="row">
        <div class="col s8"></div>
        <div class="col s4">
            <form action="${pageContext.request.contextPath}/changelang" method="post">

                <label for="language"><fmt:message key="choose.language"/></label>
                <select id="language" name="language">
                    <option value="en_US">english</option>
                    <option value="ru_RU">русский</option>
                </select>
                <input type="submit" value="<fmt:message key="lang.button"/>" class="waves-effect waves-light btn"/>

            </form>

        </div>
    </div>
<br>
    <div class="center-align">
        <form method="post" action="/">
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

        <input type="submit" value="<fmt:message key="login.button"/>" class="waves-effect waves-light btn"><br>
    </form>
</div>
<br>
</div>
<script type="text/javascript">
    document.addEventListener('DOMContentLoaded', function () {
        var elems = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elems);
    });
</script>
</body>
</html>