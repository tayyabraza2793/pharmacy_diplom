<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="#">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/css/styles.css"/>
    <title>User</title>
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
    <h>Покупатель</h>
</p>
<c:if test="${currentUser != null}">
    <div>
        <form class="form__horizontal" method="post" autocomplete="off" action="userDataServlet">
            <input type="hidden" value="medicine" name="data">
            <button type="submit">Товары</button>
        </form>
        <form class="form__horizontal" method="post" autocomplete="off" action="userDataServlet">
            <input type="hidden" value="order" name="data">
            <button type="submit">Заказы</button>
        </form>
        <form class="form__horizontal" method="post" autocomplete="off" action="userDataServlet">
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
                            <c:choose>
                                <c:when test="${servletType.equals('currentUser')}">
                                    <button class="td_button" type="submit" value="editPage"
                                            name="action">Редактировать</button>
                                    <button class="td_button" type="submit" value="delete"
                                            name="action">Удалить</button>
                                </c:when>
                                <c:when test="${servletType.equals('medicine')}">
                                    <button class="td_button" type="submit" value="order"
                                            name="action">Заказать</button>
                                </c:when>
                                <c:when test="${servletType.equals('order')}">
                                    <c:if test="${item.status.equals('Cancelled')}">
                                        <button class="td_button" disabled>Отменить</button>
                                    </c:if>
                                    <c:if test="${!item.status.equals('Cancelled')}">
                                        <button class="td_button" type="submit" value="cancel"
                                                name="action">Отменить</button>
                                    </c:if>
                                </c:when>
                            </c:choose>
                        </span>
                    </form>
                </div>
            </c:forEach>
        </div>
    </div>
</c:if>
</body>
</html>