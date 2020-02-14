<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 14.02.2020
  Time: 15:14
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vuetify/dist/vuetify.js"></script>
    <link rel="stylesheet" href="${context}/css/Application.css">
    <script src="${context}/js/Application.js"></script>
    <script src="${context}/js/core.js"></script>
    <script>
        const context = '${context}';
    </script>
</head>
<body>
    <div id="coverlet" class="coverlet"></div>
    <div id="modal" class="modal-layer"></div>
    <table border="1" width="100%" style="height: 100%">
        <tr>
            <td rowspan="2" valign="top">
                <jsp:include page="navigation/menu.jsp"/>
            </td>
            <td id="title">
                Title
            </td>
        </tr>
        <tr>
            <td style="width: 100%; height: 100%" id="content">
                Content
            </td>
        </tr>
    </table>
</body>
</html>
