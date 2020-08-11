
<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 13.06.2020
  Time: 15:56
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html lang="${locale}">
    <head>
        <title><fmt:message key="application.title"/></title>
        <link rel="icon" href="${context}/images/icon.svg">
        <link rel="stylesheet" href="${context}/css/main.css">
        <link rel="stylesheet" href="${context}/css/application.css">
        <link rel="stylesheet" href="${context}/css/modalLayer.css">
        <link rel="stylesheet" href="${context}/css/datePicker.css">
        <script type="application/javascript" src="${context}/external/jquery.min.js"></script>
        <script type="application/javascript" src="${context}/external/vue.js"></script>
        <script type="application/javascript" src="${context}/external/vuetify.js"></script>
        <script type="application/javascript" src="${context}/js/constants.js"></script>
        <script type="application/javascript" src="${context}/js/connection.js"></script>
        <script type="application/javascript" src="${context}/js/subscriber.js"></script>
        <script type="application/javascript" src="${context}/js/application.js"></script>
        <script type="application/javascript" src="${context}/js/utils.js"></script>
        <script type="application/javascript" src="${context}/vue/list.vue"></script>
        <script type="application/javascript" src="${context}/vue/templates/tree/treeView.vue"></script>

        <script>
            welcomeUrl = '${welcome}';
            logoutUrl = '${logout}';
            user = ${user.id};
            Vue.component('tree-view', treeView);
            if (typeof context === 'undefined'){
                context = '${context}'
            }
            SUBSCRIBE_API = '${subscribe}';
            subscriber.connect();
        </script>
    </head>
    <body>
        <div id="modalLayer" class="modal-layer"></div>
        <div class="body-wrapper">
            <div class="body">
                <div class="menu-holder">
                    <jsp:include page="navigation/navigationMenu.jsp"/>
                </div>
                <div class="content">
                    <div id="coverlet" class="load-screen">
                        <div class="loader-holder">
                            <div class="loader"></div>
                        </div>
                    </div>
                    <div class="header">
                        <div id="titleHolder" class="title-holder"></div>
                        <div id="timerWidget" class="timer-holder">
                            <div class="timer-wrapper" v-if="timer.begin != null">
                                <span class="text-button" style="color: orangered" v-on:click="timerStop()">
                                    &#9632;
                                </span>
                                <b>
                                    {{timer.task.title}}
                                </b>
                                <span>
                                    {{prettyNumber(timerLength.hours)}}:{{prettyNumber(timerLength.min)}}:{{prettyNumber(timerLength.sec)}}
                                </span>
                            </div>
                        </div>
                        <div class="personal-holder">
                            <c:set var="personalRoom"><fmt:message key="personal.room"/></c:set>
                            <span class="text-button" title="${personalRoom}" onclick="loadPage('${personal}')">
                                ${user.surname} ${user.forename}
                            </span>
                            <span class="text-button" onclick="logout()">
                                ( <fmt:message key="logout"/> )
                            </span>
                        </div>
                    </div>
                    <div class="content-holder" id="contentHolder">
                        <div style="max-height: 100%">
                            <div class="item-container">
                                <c:forEach begin="0" end="30" var="i">
                                    <div>
                                        ${i}
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="footer">
                2019
                <c:if test="${year ne 2019}">
                    - ${year}
                </c:if>
            </div>
        </div>
    </body>
<script type="application/javascript" src="${context}/vue/tree/timer.vue"></script>
<script type="application/javascript" src="${context}/vue/tree/timerWidget.vue"></script>
<script type="application/javascript">
    timerWidget.api.timerStop = '${timerStop}';
    subscriber.subscribe('${timerSubscribe}', timerWidget.handler)
</script>
</html>
