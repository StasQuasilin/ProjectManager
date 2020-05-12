<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 14.02.2020
  Time: 15:14
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>

    <script src="https://cdn.jsdelivr.net/npm/vuetify/dist/vuetify.js"></script>
    <script src="//cdn.jsdelivr.net/npm/sortablejs@1.8.4/Sortable.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/Vue.Draggable/2.20.0/vuedraggable.umd.min.js"></script>
    <script src="${context}/js/subscriber.js"></script>
    <link rel="stylesheet" href="${context}/css/Application.css">
    <link rel="stylesheet" href="${context}/css/date-picker.css">

    <script src="${context}/js/Application.js"></script>
    <script src="${context}/js/core.js"></script>
    <script src="${context}/vue/templates/progressBar.vue"></script>
    <title>PM</title>
    <script>
        Vue.component('progress-bar', bar);
        const context = '${context}';
        const UserID = ${user.id}
        subscriber.init('${socketProtocol}', window.location.host + '${socketServer}', '${socketAddress}');
    </script>
</head>
<body>
    <div id="coverlet" class="coverlet"></div>
    <div id="modal" class="modal-layer"></div>
    <table border="1" style="height: 100%; width: 100%; border-collapse: collapse">
        <tr>
            <td rowspan="2" valign="top">
                <jsp:include page="navigation/menu.jsp"/>
                <div class="filter-holder" id="filterHolder"></div>
            </td>
            <td id="title">
                Title
            </td>
        </tr>
        <tr>
            <td style="width: 100%; height: 100%; vertical-align: top" id="content">
                Content
            </td>
        </tr>
    </table>
</body>
</html>
