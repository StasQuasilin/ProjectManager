<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 05.08.20
  Time: 09:47
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <head>
        <title><fmt:message key="login.title"/></title>
        <link rel="icon" href="${context}/images/icon.svg">
        <link rel="stylesheet" href="${context}/css/main.css">
        <link rel="stylesheet" href="${context}/css/login.css">
        <script type="application/javascript" src="${context}/js/connection.js"></script>
        <script>
            if (typeof context === 'undefined'){
                context = '${context}'
            }
        </script>
    </head>
    <body>
        <div id="loginApp" class="login-page">
            <div class="login-welcome">
                <jsp:include page="welcome.jsp"/>
            </div>
            <div class="login-content-holder">
                <div class="login-content">
                    <div>
                        <h2>
                            <fmt:message key="login.title"/>
                        </h2>
                    </div>
                    <div :class="{error : errors.email}">
                        <label for="login">
                            <fmt:message key="login.login"/>
                        </label>
                        <input id="login" v-model="login.email" v-on:click="errors.email=false">
                    </div>
                    <div :class="{error : errors.password}">
                        <label for="password">
                            <fmt:message key="login.password"/>
                        </label>
                        <input id="password" type="password" v-model="login.password" v-on:click="errors.password = false">
                    </div>
                    <div style="text-align: center">
                        <button v-on:click="signIn">
                            <fmt:message key="login.enter"/>
                        </button>
                    </div>
                    <div>
                        <span class="text-button" v-on:click="registration">
                            <fmt:message key="login.not.register.yet"/>
                        </span>
                    </div>
                    <div>
                        <label for="language">
                            <fmt:message key="language"/>
                        </label>
                        <select id="language" v-model="locale" v-on:change="changeLocale()">
                            <option v-for="l in locales" :value="l">
                                {{localeNames[l]}}
                            </option>
                        </select>
                    </div>
                    <div class="error">
                        {{message}}
                    </div>
                </div>
            </div>
        </div>
    </body>
    <script type="application/javascript" src="${context}/external/vue.js"></script>
    <script type="application/javascript" src="${context}/vue/login/login.vue"></script>
    <script type="application/javascript">
        login.api.login = '${login}';
        login.api.registration = '${registration}';
        <c:forEach items="${locales}" var="l">
        login.locales.push('${l}');
        login.localeNames['${l}'] = '<fmt:message key="language.${l}"/>';
        </c:forEach>
        login.locale = '${locale}'
    </script>
</html>
