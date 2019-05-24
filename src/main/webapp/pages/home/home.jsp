<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.2.3/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vuetify/dist/vuetify.js"></script>
    <link rel="stylesheet" href="${context}/css/Main.css">
    <script src="${context}/js/core.js"></script>
    <script src="${context}/vue/Application.js"></script>
    <script>
        const context = '${context}'
    </script>
    <title>
        <fmt:message key="title"/>
    </title>
</head>
<body>
<div id="coverlet" class="coverlet"></div>
<div id="modal" class="modal">

</div>
<div id="application">
    <table border="0" style="height: 100%; width: 100%">
        <tr>
            <td rowspan="2" valign="top">
                <jsp:include page="menu.jsp"/>
            </td>
            <td width="100%">
                <div id="title" class="title">
                    TITLE
                </div>
            </td>
        </tr>
        <tr>
            <td height="100%" width="100%" valign="top" align="center">
                <div id="content">
                    CONTENT
                </div>
            </td>
        </tr>
    </table>
</div>

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