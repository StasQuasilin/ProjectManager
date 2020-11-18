<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 16.06.2020
  Time: 21:12
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div class="navigation-menu">
        <div onclick="loadPage('${goals}')"><fmt:message key="menu.goals"/></div>
        <div onclick="loadPage('${tree}')"><fmt:message key="menu.tree"/></div>
<%--        <div onclick="loadPage('${kanban}')"><fmt:message key="menu.kanban"/></div>--%>
        <div onclick="loadPage('${calendar}')"><fmt:message key="menu.calendar"/></div>
        <div onclick="loadPage('${finances}')"><fmt:message key="menu.finances"/></div>
        <div onclick="loadPage('${friends}')"><fmt:message key="menu.friends"/></div>
<%--        <div onclick="loadPage('${messages}')"><fmt:message key="menu.messages"/></div>--%>
    </div>
</html>
