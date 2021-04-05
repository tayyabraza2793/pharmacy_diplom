<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Edit current user</title>
</head>
<body>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<c:set var="status" value="${requestScope.status}"/>
<c:set var="exceptions" value="${requestScope.exceptions}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<div>
    <p>
        <h>Редактирование пользователя</h>
    </p>
</div>
<c:if test="${status == null}">
    <div>
        <div>
            <c:if test="${currentUser == null}">
                <c:redirect url="/loginPage"/>
            </c:if>
            <form method="post" autocomplete="off" action="updateUserServlet">
                <label for="name">Имя:</label><br>
                <input id="name" name="name" value="${currentUser.name}"><br>
                <label for="secondName">Фамилия:</label><br>
                <input id="secondName" name="second name" value="${currentUser.secondName}"><br>
                <label for="email">Эл. почта:</label><br>
                <input id="email" type="email" name="email" value="${currentUser.email}"><br>
                <label for="phone">Телефон:</label><br>
                <input id="phone" name="phone" value="${currentUser.phone}"><br>
                <label for="role">Роль:</label><br>
                <select id="role" name="role" value="${currentUser.role}">
                    <option value="admin">Администратор</option>
                    <option value="pharmacist">Фармацевт</option>
                    <option value="customer">Покупатель</option>
                </select><br>
                <label for="oldPassword">Старый пароль:</label><br>
                <input id="oldPassword" type="password" name="old password"><br>
                <label for="newPassword">Новый пароль:</label><br>
                <input id="newPassword" type="password" name="password"><br>
                <label for="confirmPassword">Повторите пароль:</label><br>
                <input id="confirmPassword" type="password" name="confirm password"><br>
                <button type="submit">Подтвердить</button>
            </form>
        </div>
        <div>
            <form method="get" autocomplete="off" action="${currentUser.role}Page">
                <button type="submit">Назад</button>
            </form>
        </div>
    </div>
</c:if>
<c:if test="${status != null}">
    <c:choose>
        <c:when test="${status.equals('updated')}">
            <div>
                <form method="get" autocomplete="off" action="${currentUser.role}Page">
                    <label>User successfully updated</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:when>
        <c:otherwise>
            ${requestScope.remove(status)}
            <div>
                <form method="get" autocomplete="off" action="currentUserPage">
                    <label>User was not updated</label><br>
                    <button type="submit">Ok</button>
                </form>
            </div>
        </c:otherwise>
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

<%--<c:forEach var="field" items="${currentUser['class'].declaredFields}">--%>
<%--    <c:if test="${!field.name.equals('password') && !field.name.equals('salt')}">--%>
<%--        <c:choose>--%>
<%--            <c:when test="${field.name.equals('id')}">--%>
<%--                <label for="${field.name}">${field.name}:</label><br>--%>
<%--                <input id="${field.name}" type="text" value="${currentUser[field.name]}"--%>
<%--                       name="${field.name}"><br>--%>
<%--            </c:when>--%>
<%--            <c:when test="${field.name.equals('email')}">--%>
<%--                <label for="${field.name}">${field.name}:</label><br>--%>
<%--                <input id="${field.name}" type="email" value="${currentUser[field.name]}"--%>
<%--                       name="${field.name}"><br>--%>
<%--            </c:when>--%>
<%--            <c:otherwise>--%>
<%--                <label for="${field.name}">${field.name}:</label><br>--%>
<%--                <input id="${field.name}" value="${currentUser[field.name]}" name="${field.name}"><br>--%>
<%--            </c:otherwise>--%>
<%--        </c:choose>--%>
<%--    </c:if>--%>
<%--</c:forEach>--%>
