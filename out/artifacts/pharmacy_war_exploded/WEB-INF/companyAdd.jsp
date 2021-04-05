<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Add company</title>
</head>
<body>
<c:set var="exceptions" value="${requestScope.exceptions}"/>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<c:set var="companyCreated" value="${requestScope.companyCreated}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<c:if test="${companyCreated == null}">
    <div>
        <form method="post" autocomplete="off" action="companyServlet">
            <label for="name">Имя:</label><br>
            <input id="name" name="name"><br>
            <label for="description">Описание:</label><br>
            <input id="description" name="description"><br>
            <label for="email">Эл.почта:</label><br>
            <input id="email" type="email" name="email"><br>
            <label for="address">Адрес:</label><br>
            <input id="address" name="address"><br>
            <label for="phone">Телефон:</label><br>
            <input id="phone" name="phone"><br>
            <button type="submit" name="action" value="add">Добавить</button>
        </form>
    </div>
    <div>
        <form action="${currentUser.role}Page">
            <button type="submit">Отмена</button>
        </form>
    </div>
</c:if>
<c:if test="${companyCreated != null}">
    <c:choose>
        <c:when test="${companyCreated.equals('true')}">
            <div>
                <form method="get" autocomplete="off" action="${currentUser.role}Page">
                    <label>Company successfully created</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:when>
        <c:when test="${companyCreated.equals('false')}">
            <div>
                <form method="get" autocomplete="off" action="/companyAddPage">
                    <label>Company was not created</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:when>
    </c:choose>
</c:if>
<c:if test="${exceptions != null}">
    <div>
        <c:forEach var="exceptions" items="${exceptions}">
            <p>
                <label>${exceptions}</label>
            </p>
        </c:forEach>
    </div>
</c:if>
</body>
</html>
