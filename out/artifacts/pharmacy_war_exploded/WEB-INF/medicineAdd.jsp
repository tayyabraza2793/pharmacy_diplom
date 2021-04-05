<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Add medicine</title>
</head>
<body>
<c:set var="exceptions" value="${requestScope.exceptions}"/>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<c:set var="medicineCreated" value="${requestScope.medicineCreated}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<c:if test="${medicineCreated == null}">
    <div>
        <form method="post" autocomplete="off" action="medicineServlet">
            <label for="company_id">Ид компании:</label><br>
            <input id="company_id" name="company_id"><br>
            <label for="name">Название:</label><br>
            <input id="name" name="name"><br>
            <label for="type">Тип:</label><br>
            <input id="type" name="type"><br>
            <label for="man_date">Дата изготовления:</label><br>
            <input id="man_date" name="man_date"><br>
            <label for="exp_date">Срок годности:</label><br>
            <input id="exp_date" name="exp_date"><br>
            <label for="buy_price">Цена покупки:</label><br>
            <input id="buy_price" name="buy_price"><br>
            <label for="sell_price">Цена продажи:</label><br>
            <input id="sell_price" name="sell_price"><br>
            <label for="index">Индекс:</label><br>
            <input id="index" name="index"><br>
            <label for="quantity">Количество:</label><br>
            <input id="quantity" name="quantity"><br>
            <label for="tablets_count">Количество таблеток:</label><br>
            <input id="tablets_count" name="tablets_count"><br>
            <label for="dosage">Дозировка:</label><br>
            <input id="dosage" name="dosage"><br>
            <label for="description">Описание:</label><br>
            <input id="description" name="description"><br>
            <button type="submit" name="action" value="add">Добавить</button>
        </form>
    </div>
    <div>
        <form action="${currentUser.role}Page">
            <button type="submit">Отмена</button>
        </form>
    </div>
</c:if>
<c:if test="${medicineCreated != null}">
    <c:choose>
        <c:when test="${medicineCreated.equals('true')}">
            <div>
                <form method="get" autocomplete="off" action="${currentUser.role}Page">
                    <label>Medicine successfully created</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:when>
        <c:when test="${medicineCreated.equals('false')}">
            <div>
                <form method="get" autocomplete="off" action="/medicineAddPage">
                    <label>Medicine was not created</label><br>
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
