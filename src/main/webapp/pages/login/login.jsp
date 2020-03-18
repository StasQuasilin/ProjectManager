<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <head>
        <title>
            <fmt:message key="${title}"/>
        </title>
        <script src="${context}/ext/vue.js"></script>
        <script src="${context}/ext/vuetify.js"></script>
        <script src="${context}/js/core.js"></script>
        <script>
            const context = '${context}';
        </script>
    </head>
<link rel="stylesheet" href="${context}/css/login.css">
    <body>
        <table style="height: 100%; width: 100%" id="login">
            <tr>
               <td align="center">
                   <div align="center" style="width: 240pt">
                       <H2 align="center"><fmt:message key="${title}"/> </H2>
                       <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt">
                           <label for="email">
                                <span>
                                    <fmt:message key="login.email"/>
                                </span>
                           </label>
                           <input type="text" v-model="email" id="email" autocomplete="off">
                       </div>
                       <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt">
                           <label for="password">
                                <span>
                                    <fmt:message key="login.password"/>
                                </span>
                           </label>
                           <input type="password" v-model="password" id="password" autocomplete="off">
                       </div>
                       <button v-on:click="login()">
                           <fmt:message key="login.sign.in"/>
                       </button>
                       <div style="padding-top: 4pt;">
                           <a href="${context}/registration">
                               <fmt:message key="login.registration"/>
                           </a>
                           <a href="${context}/restore">
                               <fmt:message key="login.password.restore"/>
                           </a>
                       </div>
                       <span class="alert" id="alert">&nbsp;</span>
                   </div>
               </td>
            </tr>
        </table>
    <script src="${context}/vue/login/login.vue"></script>
    <script>
        login.api.login = '${login}';
    </script>
    </body>
</html>