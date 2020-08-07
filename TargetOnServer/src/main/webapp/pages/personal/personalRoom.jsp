
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 20.07.20
  Time: 14:18
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/personalRoom.css">
<div class="room">
    <div class="room-colon">
        <div class="room-colon-cell">
            <div>
                <img src="${context}/images/avatart.jpg" alt="" style="width: 200pt">
            </div>
        </div>
        <div class="room-colon-cell">
            <div class="cell-title">
                <fmt:message key="personal.info"/>
            </div>
            <div>
                <fmt:message key="person.surname"/>:
                ${user.surname}
            </div>
            <div>
                <fmt:message key="person.forename"/>:
                ${user.forename}
            </div>
        </div>
        <div class="room-colon-cell">
            <div class="cell-title">
                <fmt:message key="personal.security"/>
            </div>
            <div>
                <button>
                    <fmt:message key="personal.change.password"/>
                </button>
            </div>
        </div>
    </div>
    <div class="room-colon">
        <div class="room-colon-cell">
            <div class="cell-title">
                <fmt:message key="personal.currency"/>
            </div>
        </div>
    </div>
</div>
