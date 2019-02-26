<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${context}/css/tree/TreeList.css">
    <script src="${context}/js/tree/TreeList.js"></script>
</head>
<body>
<table style="height: 100%; width: 100%">
    <tr>
        <td>
            <div class="tree-title">
                <div id="tree-title" style="display: inherit;"></div>
                <div class="tree-menu">
                    <div class="add" id="add-child"></div>
                </div>
            </div>
        </td>
        <td rowspan="2" valign="top">
            <jsp:include page="TreeView.jsp"/>
        </td>
    </tr>
    <tr>
        <td height="100%" width="100%" valign="top">
            <div id="child-box" class="child-box"></div>
        </td>
    </tr>
    <div id="task-child-instance" class="task-child" style="display: none">
        <span id="child-title"></span>
        <div class="tree-menu">
            <c:set var="editLabel"><fmt:message key="edit"/></c:set>
            <c:set var="deleteLabel"><fmt:message key="delete"/></c:set>
            <div class="edit" title="${editLabel}"></div>
            <div class="delete" title="${deleteLabel}"></div>
        </div>
    </div>
</table>
</body>
</html>
