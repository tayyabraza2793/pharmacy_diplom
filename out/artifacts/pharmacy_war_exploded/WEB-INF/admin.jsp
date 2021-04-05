<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>Admin</title>
</head>
<body>
<c:set var="currentUser" value="${sessionScope.currentUser}"/>
<c:set var="returnedData" value="${requestScope.returnedData}"/>
<c:set var="dataFields" value="${requestScope.dataFields}"/>
<c:set var="servletType" value="${requestScope.type}"/>
<div>
    <img src="${pageContext.request.contextPath}/images/logo.png" class="image" alt="pharmacy"
         style="display: inline-block">
</div>
<div>
    <h class="label">Pharmacy project</h>
</div>
<p>
    <h>Администратор</h>
</p>
<c:if test="${currentUser != null}">
    <div>
        <form class="form__horizontal" method="post" autocomplete="off" action="adminDataServlet">
            <input type="hidden" value="user" name="data">
            <button type="submit" value="user">Пользователи</button>
        </form>
        <form class="form__horizontal" method="post" autocomplete="off" action="adminDataServlet">
            <input type="hidden" value="medicine" name="data">
            <button type="submit">Товары</button>
        </form>
        <form class="form__horizontal" method="post" autocomplete="off" action="adminDataServlet">
            <input type="hidden" value="order" name="data">
            <button type="submit">Заказы</button>
        </form>
        <form class="form__horizontal" method="post" autocomplete="off" action="adminDataServlet">
            <input type="hidden" value="company" name="data">
            <button type="submit">Компании</button>
        </form>
        <form class="form__horizontal" method="post" autocomplete="off" action="adminDataServlet">
            <button type="submit" value="account" name="data">Аккаунт</button>
        </form>
        <form class="form__horizontal" method="get" autocomplete="off" action="logoutServlet">
            <button type="submit">Выход</button>
        </form>
    </div>
</c:if>
<c:if test="${returnedData != null}">
    <div>
        <div class="table">
            <div class="tr">
                <c:forEach var="field" items="${dataFields}">
                    <c:if test="${!field.equals('Пароль') && !field.equals('Соль')}">
                        <span class="td">${field}</span>
                    </c:if>
                </c:forEach>
                <span class="td">Действие</span>
            </div>

            <c:forEach var="item" items="${returnedData}">
                <div class="tr">
                    <form method="post" autocomplete="off" action="${servletType}Servlet">
                        <c:forEach var="field" items="${item['class'].declaredFields}">
                            <c:if test="${!field.name.equals('password') && !field.name.equals('salt')}">
                                <input type="hidden" value="${item[field.name]}" name="${field.name}">
                                <c:choose>
                                    <c:when test="${field.name.equals('buyPrice') || field.name.equals('sellPrice')}">
                                        <span class="td">${item[field.name]} руб.</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="td">${item[field.name]}</span>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                        </c:forEach>
                        <span class="td">
                            <c:if test="${servletType.equals('currentUser')}">
                                <button class="td_button" type="submit" value="editPage"
                                        name="action">Редактировать</button>
                            </c:if>
                            <button class="td_button" type="submit" value="delete" name="action">Удалить</button>
                        </span>
                    </form>
                </div>
            </c:forEach>
            <p></p>
            <c:if test="${!servletType.equals('currentUser') && !servletType.equals('order')}">
                <form method="get" action="${servletType}AddPage">
                    <button type="submit">Добавить</button>
                </form>
            </c:if>
        </div>
    </div>
</c:if>
</body>
</html>

<%--        <table>--%>
<%--            <thead>--%>
<%--            <tr>--%>
<%--                <c:forEach var="field" items="${dataFields}">--%>
<%--                    <td>${field}</td>--%>
<%--                </c:forEach>--%>
<%--            </tr>--%>
<%--            </thead>--%>
<%--            <tbody>--%>
<%--            <c:forEach var="dt" items="${returnedData}">--%>
<%--                <tr>--%>
<%--                    <c:forEach var="field" items="${dt['class'].declaredFields}">--%>
<%--                        <td>${dt[field.name]}</td>--%>
<%--                    </c:forEach>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>
