<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.2.3/jquery.min.js"></script>
</head>
<body>
<table border="1" style="height: 100%; width: 100%">
    <tr>
        <td rowspan="2" valign="top">
            <ul class="menu">
                <li class="menu-item" onclick="location.href='${context}/projects.j'">
                    <span><fmt:message key="menu.projects"/></span>
                </li>
                <li class="menu-item" onclick="location.href='${context}/tree.j'">
                    <span><fmt:message key="menu.task.tree"/></span>
                </li>
                <li class="menu-item" onclick="location.href='${context}/calendar.j'">
                    <span><fmt:message key="menu.calendar"/></span>
                </li>
                <li class="menu-item" onclick="location.href='${context}/budget.j'">
                    <span><fmt:message key="menu.budget"/></span>
                </li>
                <li class="menu-item" onclick="location.href='${context}/messages.j'">
                    <span><fmt:message key="menu.messages"/></span>
                </li>
                <li class="menu-item"  onclick="location.href='${context}/commandos.j'">
                    <span><fmt:message key="menu.commandos"/></span>
                </li>
                <li class="menu-item" onclick="location.href='${context}/settings.j'">
                    <span><fmt:message key="menu.settings"/></span>
                </li>
            </ul>
        </td>
        <td width="100%">
            <div class="title">
                <span >
                    <fmt:message key="${title}"/>
                </span>
            </div>
        </td>
    </tr>
    <tr>
        <td height="100%" width="100%" valign="top" align="center">
            <jsp:include page="${pageContent}"/>
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <span style="">
                &copy;
            </span>
            <span class="footer" id="footer">
                2019 Project Manager
                <a href="mailto:pm.support@gmail.com">pm.support@gmail.com</a>
            </span>
        </td>
    </tr>
</table>
<%--<div style="display: flex">--%>
<%--<div>--%>

<%--</div>--%>
<%--<div>--%>
    <%--<span>--%>
        <%--Title--%>
    <%--</span>--%>
    <%--<div style="height: 100%">--%>
        <%--CONTENT--%>
    <%--</div>--%>
<%--</div>--%>
<%--</div>--%>
</body>
</html>