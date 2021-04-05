<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Login</title>
</head>
<c:set var="exception" value="${requestScope.exception}"/>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<div>
    <form method="post" autocomplete="off" action="loginServlet">
        <p>
            <label for="login">Эл. почта:</label><br>
            <input id="login" type="email" name="login">
        </p>
        <p>
            <label for="password">Пароль:</label><br>
            <input id="password" name="password" type="password">
        </p>
        <p>
            <button type="submit">Вход</button>
        </p>
    </form>
</div>
<div>
    <form method="get" action="registrationPage">
        <p>
            <button type="submit">Регистрация</button>
        </p>
    </form>
</div>
<c:if test="${currentUser != null}">
    <c:redirect url="${currentUser.role}Page"/>
</c:if>
<c:if test="${exception != null}">
    <div>
        <p>
            <label>${exception}</label>
        </p>
    </div>
</c:if>
</body>
</html>
