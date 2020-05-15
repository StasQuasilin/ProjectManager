<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 14.04.20
  Time: 10:33
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/transactions/fastTransactions.css">
    <table id="fast" style="width: 100%; height: 100%;">
        <tr>
            <td class="transaction-title">
                <span>
                    <fmt:message key="title.fast.transactions"/>
                </span>
                <span class="mini-button" v-on:click="edit()">
                    + <fmt:message key="button.add"/>
                </span>
            </td>
        </tr>
        <tr>
            <td style="height: 100%">
                <div class="fast-container" v-if="items.length > 0">
                    <div v-for="item in items">
                        {{item}}
                    </div>
                </div>
                <div v-else>
                    <fmt:message key="fast.transactions.tips"/>
                </div>
            </td>
        </tr>
    </table>
</html>
