<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/Menu.css">
<div class="menu">
    <div class="menu-item" onclick="loadPage('${projects}')">
        <fmt:message key="menu.projects"/>
    </div>
    <div class="menu-item" onclick="loadPage('${tree}')">
        <fmt:message key="menu.task.tree"/>
    </div>
    <div class="menu-item" onclick="loadPage('${calendar}')">
        <fmt:message key="menu.calendar"/>
    </div>
    <div class="menu-item" onclick="loadPage('${account}')">
        <fmt:message key="menu.account"/>
    </div>
    <div class="menu-item" onclick="loadPage('${payments}')">
        <fmt:message key="menu.payments"/>
    </div>
    <div class="menu-item" onclick="loadPage('${settings}')">
        <fmt:message key="menu.settings"/>
    </div>
</div>
</html>