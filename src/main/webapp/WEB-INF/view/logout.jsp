<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 24/09/2019
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLang}"/>
<fmt:setBundle basename="pages/logout"/>


<form action="/logout" method="post">
    <input type="submit" value="<fmt:message key="logout.button"/>" class="waves-effect waves-light btn-small"/>
</form>
