<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 21/08/2019
  Time: 22:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.currentLang}"/>
<fmt:setBundle basename="pages/adminpage"/>

<html>
<head>
    <title>Title</title>
    <%@ include file="setup.jsp"%>
</head>
<body>
<div class="container">
<fmt:message key="greeting"/>
<c:out value="${sessionScope.user.firstName} ${sessionScope.user.lastName}))"/><br>

<c:if test="${empty requestScope.allUserRequests}">
    <fmt:message key="no.requests"/>
</c:if>

<c:if test="${not empty requestScope.allUserRequests}">
    <table>
        <tr>
            <th><fmt:message key="user.login"/></th>
            <th><fmt:message key="user.balance"/></th>
            <th><fmt:message key="expected.limit"/></th>
            <th><fmt:message key="date.of.validity"/></th>
        </tr>
        <c:forEach items="${requestScope.allUserRequests}" var="request">
            <tr>
                <td>${request.userLogin.usersLogin}</td>
                <td>${request.userTotalBalance}</td>
                <td>${request.expectedCreditLimit}</td>
                <td>
                    <fmt:message key="account.validity.message"/>
                    <br>
                        ${request.dateOfEndCredit}
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}adminpage/confirmrequest">
                        <label>
                            <input hidden name="requestId" value="${request.id}"/>
                        </label>
                        <input type="submit" value="<fmt:message key="accept.request"/>"
                               class="waves-effect waves-light btn">
                    </form>
                </td>
                <td>
                    <form method="post" action="${pageContext.request.contextPath}adminpage/deleterequest">
                        <label>
                            <input hidden name="requestId" value="${request.id}"/>
                        </label>
                        <input type="submit" value="<fmt:message key="delete.request"/>"
                               class="waves-effect waves-light btn"/>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<br>
<br>
<form method="get" action="${pageContext.request.contextPath}adminpage/newuserpage">
    <input type="submit" value="<fmt:message key="newUser.button" />" class="waves-effect waves-light btn"/>
</form>
<br>
<jsp:include page="logout.jsp"/>
</div>
</body>
</html>
