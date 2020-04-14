<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 14.04.20
  Time: 14:03
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <table id="accounts" style="height: 100%; width: 100%">
        <tr>
            <td style="text-align: center">
                <fmt:message key="accounts.title"/>
                <span class="add-button" v-on:click="edit">
                    <fmt:message key="button.add"/>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <span v-for="(t, key, idx) in totalAmount()">
                {{t.toLocaleString()}}
                <span v-if="key !== 'null'">
                    {{key}}
                </span>
            </span>
            </td>
        </tr>
        <tr>
            <td style="height: 100%">
                <div style="height: 100%; overflow-y: scroll">
                    <div v-for="item in items" v-on:click="edit(item)">
                        {{item.title}}: {{item.limit+item.amount}} {{item.currency}}
                    </div>
                </div>
            </td>
        </tr>
    </table>
</html>
