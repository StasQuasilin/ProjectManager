<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 14.02.2020
  Time: 15:28
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/navigationMenu.css">
<div class="menu">
    <div class="menu-item" onclick="loadPage('${projects}')">
        <fmt:message key="menu.projects"/>
    </div>
    <div class="menu-item" onclick="loadPage('${kanban}')">
        <fmt:message key="menu.kanban"/>
    </div>
    <div class="menu-item" onclick="loadPage('${calendar}')">
        <fmt:message key="menu.calendar"/>
    </div>
    <div class="menu-item" onclick="loadPage('${transactions}')">
        <fmt:message key="menu.transactions"/>
    </div>
    <div class="menu-item" onclick="loadPage('${budget}')">
        <fmt:message key="menu.budget"/>
    </div>
    <div class="menu-item">
        <fmt:message key="menu.messages"/>
    </div>
    <div class="menu-item" onclick="loadPage('${settings}')">
        <fmt:message key="menu.settings"/>
    </div>
</div>
</html>
