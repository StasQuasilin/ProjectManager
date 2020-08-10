<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 24.07.20
  Time: 10:13
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/remover.vue"></script>
<script>
    remover.api.onCancel = '${edit}';
    remover.api.remove = '${remove}';
    remover.object = ${transaction.id};
</script>
<table id="remover">
    <tr>
        <td colspan="2">
            <fmt:message key="transaction.remove.question"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transaction.date"/>
        </td>
        <td>
            <fmt:formatDate value="${transaction.date}" pattern="dd.MM.yyyy"/>
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="transaction.category"/>
        </td>
        <td>
            ${transaction.category.title}
        </td>
    </tr>
    <tr>
        <td>
            <fmt:message key="tranaction.sum"/>
        </td>
        <td>
            ${transaction.amount} ${transaction.currency}
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <button v-on:click="cancel">
                <fmt:message key="button.no"/>
            </button>
            <button v-on:click="remove()">
                <fmt:message key="button.yes"/>
            </button>
        </td>
    </tr>
</table>
