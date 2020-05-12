<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div id="buyList" style="height: 100%">
        <div style="width: 100%; text-align: center">
            <span>
                <fmt:message key="buy.list.title"/>
            </span>
            <span class="mini-button" v-on:click="edit()">
                + <fmt:message key="button.add"/>
            </span>
        </div>
        <div style="height: 100%; width: 100%; border: solid 1pt">
            Lolo
        </div>
    </div>
</html>