<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 30.06.2020
  Time: 22:03
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<div class="modal-holder">
    <div class="modal-window">
        <div class="modal-title">
            &nbsp;
            <c:if test="${not empty title}">
                <fmt:message key="${title}"/>
            </c:if>
            <span class="close-modal-button text-button" onclick="closeModal()">
                &times;
            </span>
        </div>
        <div class="modal-content">
            <jsp:include page="${content}"/>
        </div>
    </div>
</div>
