<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${context}/css/tree/TreeList.css">
</head>
<body>
<table style="height: 100%; width: 100%">
    <tr>
        <td>
            <div id="tree-title" class="tree-title">
                <span class="tree-title-item">Root</span> /
                <span class="tree-title-item">Child 1</span> /
                <span class="tree-title-item">Child 2</span> /
                <span class="tree-title-item">Child 3</span> /
                <span class="tree-title-item">Child 4</span>

            </div>
        </td>
        <td rowspan="2" valign="top">
            <jsp:include page="TreeView.jsp"/>
        </td>
    </tr>
    <tr>
        <td height="100%" width="100%" valign="top">
            <div id="child-box" class="child-box">
                Child
            </div>
        </td>
    </tr>
</table>
</body>
</html>
