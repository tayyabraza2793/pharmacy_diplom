<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Add order</title>
</head>
<body>
<c:set var="exceptions" value="${requestScope.exceptions}"/>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<c:set var="orderCreated" value="${requestScope.orderCreated}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<c:if test="${orderCreated == null}">
    <div>
        <form method="post" autocomplete="off" action="orderServlet">
            <label for="user_id">Ид пользователя:</label><br>
            <input id="user_id" name="userId" value="${currentUser.id}"><br>
            <label for="medicine_id">Ид товара:</label><br>
            <input id="medicine_id" name="medicineId" value="${requestScope.medicine_id}"><br>
            <label for="quantity">Количество</label><br>
            <input id="quantity" name="quantity"><br>
            <input name="status" value="Ready" type="hidden">
            <button type="submit" name="action" value="add">Заказать</button>
        </form>
    </div>
    <div>
        <form action="${currentUser.role}Page">
            <button type="submit">Отмена</button>
        </form>
    </div>
</c:if>
<c:if test="${orderCreated != null}">
    <c:choose>
        <c:when test="${orderCreated.equals('true')}">
            <div>
                <form method="get" autocomplete="off" action="${currentUser.role}Page">
                    <label>Order successfully created</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:when>
        <c:when test="${orderCreated.equals('false')}">
            <div>
                <form method="get" autocomplete="off" action="/orderAddPage">
                    <label>Order was not created</label><br>
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
