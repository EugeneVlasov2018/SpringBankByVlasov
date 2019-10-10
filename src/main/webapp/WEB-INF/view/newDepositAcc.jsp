<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 24/09/2019
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLang}"/>
<fmt:setBundle basename="pages/newdepositacc"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="setup.jsp"/>
</head>
<body>
<div class="container">
    <form method="post" action="">
        <label>
            <input name="deposit" type="text" placeholder="<fmt:message key="initial.fee"/>"/>
        </label>
        <c:if test="${requestScope.zeropal==true}">
            <fmt:message key="pal.is.zero"/><br>
        </c:if>
        <input type="submit" value="<fmt:message key="create.depositacc"/> "
               class="waves-effect waves-light btn-small"/>
    </form>
</div>
</body>
</html>
