<%--
  Created by IntelliJ IDEA.
  User: quasilin
  Date: 24.02.2019
  Time: 10:24
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div style="background-color: pink; width: 100%; height: 100%">
        <button onclick="loadModal('${currencySettings}')">
            <fmt:message key="currency.settings"/>
        </button>
    </div>
</html>
