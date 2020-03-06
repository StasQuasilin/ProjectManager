<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<title>
    <fmt:message key="${title}"/>
</title>
<head>
    <script src="${context}/js/login/login.js"></script>
</head>
<link rel="stylesheet" href="${context}/css/login.css">
<body>

    <table style="height: 100%; width: 100%">
        <tr>
           <td align="center">
               <div align="center" style="width: 240pt">
                   <H2 align="center"><fmt:message key="${title}"/> </H2>
                   <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt">
                       <label for="login">
                            <span>
                                <fmt:message key="login.email"/>
                            </span>
                       </label>
                       <input type="text" id="login" autocomplete="off">
                   </div>
                   <div style="padding-bottom: 8pt; text-align: right; padding-right: 40pt">
                       <label for="password">
                            <span>
                                <fmt:message key="login.password"/>
                            </span>
                       </label>
                       <input type="password" id="password" autocomplete="off">
                   </div>
                   <button onclick="login()">
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

</body>

</html>