<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 17/09/2019
  Time: 13:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLang}" scope="session"/>
<fmt:setBundle basename="pages/addmoney"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="setup.jsp"/>
</head>
<body>
<div class="container">
    <form method="post" action="${pageContext.request.contextPath}refillacc">
        <label>
            <input type="text" name="summ" placeholder="<fmt:message key="summ"/>" pattern="^[0-9]*[.,]?[0-9]+$"
                   required>
        </label>
        <fmt:message key="positive.number"/><br>
        <input type="submit" value="<fmt:message key="button.refill"/> " class="waves-effect waves-light btn"/>
    </form>
</div>
</body>
</html>
