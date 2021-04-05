<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Add user</title>
</head>
<body>
<c:set var="exceptions" value="${requestScope.exceptions}"/>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<c:set var="userCreated" value="${requestScope.userCreated}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<c:if test="${userCreated == null}">
    <div>
        <form method="post" autocomplete="off" action="userServlet">
            <label for="name">Имя:</label><br>
            <input id="name" name="name"><br>
            <label for="secondName">Фамилия:</label><br>
            <input id="secondName" name="second name"><br>
            <label for="email">Эл. почта:</label><br>
            <input id="email" type="email" name="email"><br>
            <label for="phone">Телефон:</label><br>
            <input id="phone" name="phone"><br>
            <label for="password">Пароль:</label><br>
            <input id="password" name="password" type="password"><br>
            <label for="confirmPassword">Повторите пароль:</label><br>
            <input id="confirmPassword" name="confirm password" type="password"><br>
            <label for="role">Роль:</label><br>
            <select id="role" name="role">
                <option value="admin">Администратор</option>
                <option value="pharmacist">Фармацевт</option>
                <option value="customer">Покупатель</option>
            </select><br>
            <button type="submit" name="action" value="add">Добавить</button>
        </form>
    </div>
    <div>
        <form action="${currentUser.role}Page">
            <button type="submit">Отмена</button>
        </form>
    </div>
</c:if>
<c:if test="${userCreated != null}">
    <c:choose>
        <c:when test="${userCreated.equals('true')}">
            <div>
                <form method="get" autocomplete="off" action="${currentUser.role}Page">
                    <label>User successfully created</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:when>
        <c:when test="${userCreated.equals('false')}">
            <div>
                <form method="get" autocomplete="off" action="/userAddPage">
                    <label>User was not created</label><br>
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
