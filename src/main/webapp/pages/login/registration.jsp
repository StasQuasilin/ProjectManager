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
<body>
    <table  style="height: 100%; width: 100%" >
        <tr>
            <td align="center" id="registrator">
                <div align="center" style="width: 240pt">
                    <H2 align="center"><fmt:message key="${title}"/> </H2>
                    <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt" :class="{error : errors.email}">
                        <label for="surname">
                                <span>
                                    <fmt:message key="person.surname"/>
                                </span>
                        </label>
                        <input type="text" id="surname" v-model="surname" autocomplete="off">
                    </div>
                    <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt" :class="{error : errors.email}">
                        <label for="forename">
                                <span>
                                    <fmt:message key="person.forename"/>
                                </span>
                        </label>
                        <input type="text" id="forename" v-model="forename" autocomplete="off">
                    </div>
                    <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt" :class="{error : errors.email}">
                        <label for="login">
                                <span>
                                    <fmt:message key="login.email"/>
                                </span>
                        </label>
                        <input type="text" id="login" v-model="email" autocomplete="off">
                    </div>
                    <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt">
                        <label for="password">
                                <span>
                                    <fmt:message key="login.password"/>
                                </span>
                        </label>
                        <input type="password" id="password" v-model="password" autocomplete="off">
                    </div>
                    <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt">
                        <label for="language">
                            <span>
                                <fmt:message key="language"/>
                            </span>
                        </label>
                        <select id="language" v-model="language">
                            <option v-for="l in languages" :value="l">
                                {{l}}
                            </option>
                        </select>
                    </div>
                    <div style="padding-top: 4pt;">
                        <button onclick="location.href='${context}/login'">
                            < <fmt:message key="buttons.back"/>
                        </button>
                        <button v-on:click="registration()">
                            <fmt:message key="registration.sign.up"/>
                        </button>
                    </div>
                    <span class="alert" id="alert">&nbsp;</span>
                </div>
            </td>
        </tr>
    </table>
    <script src="${context}/vue/registration/registration.vue"></script>
    <script>
        registration.api.registration = '${registration}';
        <c:forEach items="${languages}" var="language">
        registration.languages.push('${language}');
        </c:forEach>
        registration.language = registration.languages[0];
    </script>
</body>
</html>