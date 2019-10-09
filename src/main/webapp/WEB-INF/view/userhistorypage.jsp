<%--
  Created by IntelliJ IDEA.
  User: negat
  Date: 15/09/2019
  Time: 18:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="/WEB-INF/mytag.tld" prefix="customTag" %>
<fmt:setLocale value="${sessionScope.currentLang}" scope="session"/>
<fmt:setBundle basename="pages/userhistorypage"/>
<html>
<head>
    <title>Title</title>
    <jsp:include page="setup.jsp"/>
</head>
<body>
<div class="container">
    <fmt:message key="account.number"/>
    <c:out value="${sessionScope.accountNumber}"/>
    <br/>
    <br/>
    <form action="${pageContext.request.contextPath}workwithaccount" method="get">

        <div class="row">
            <div class="col s12">
                <label>
                    <input name="typeofwork" type="radio" value="addmoney" required checked/>
                    <span><fmt:message key="addmoney"/></span>
                </label>
            </div>
            <div class="col s12">
                <label>
                    <input name="typeofwork" type="radio" value="sendmoney" required/>
                    <span><fmt:message key="sending.money"/></span>
                </label>
            </div>
        </div>

        <div class="row">
            <div class="col s12">
                <input type="submit" value="<fmt:message key="button.workwithacc"/>"
                       class="waves-effect waves-light btn"/>
            </div>
        </div>
    </form>
    <br>
    <table>
        <tr>
            <th><fmt:message key="acc.date.of.transaction"/></th>
            <th><fmt:message key="acc.transaction.amount"/></th>
            <th><fmt:message key="acc.current.balance"/></th>
            <th><fmt:message key="acc.notification"/></th>
        </tr>
        <c:forEach items="${requestScope.history}" var="acc">
            <tr>

                <td><customTag:customDate localDateTime="${acc.dateOfTransaction}"/></td>
                <td>${acc.transactionAmount}</td>
                <td>${acc.currentBalance}</td>
                <td>${acc.notification}</td>
            </tr>
        </c:forEach>
    </table>
    <br>

    <div class="row">
        <div class="col s3">
            <c:if test="${requestScope.currentPage != 1}">
                <a href="accountoperation?page=${requestScope.currentPage - 1}"><fmt:message key="previous"/></a>
            </c:if>
        </div>
        <c:forEach begin="1" end="${requestScope.numberOfPage}" var="i">
            <c:choose>
                <c:when test="${requestScope.currentPage eq i}">
                    <div class="col s1">${i}</div>
                </c:when>
                <c:otherwise>
                    <div class="col s1"><a href="accountoperation?page=${i}">${i}</a></div>
                </c:otherwise>
            </c:choose>
        </c:forEach>

        <div class="col s3">
            <c:if test="${requestScope.currentPage lt requestScope.numberOfPage}">
                <a href="accountoperation?page=${requestScope.currentPage + 1}"><fmt:message key="next"/></a>
            </c:if>
        </div>
    </div>
    <br>
    <c:out value="${requestScope.exceptionError}"/>
    <br>
    <br>
    <jsp:include page="logout.jsp"/>
</div>
</body>
</html>
