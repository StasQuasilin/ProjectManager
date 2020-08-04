<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 18.06.2020
  Time: 18:27
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<c:if test="${not empty title}">
    <div id="title" class="title-content">
        <fmt:message key="${title}"/>
    </div>
</c:if>
<jsp:include page="${content}"/>
