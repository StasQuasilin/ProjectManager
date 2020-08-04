<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 29.07.20
  Time: 15:46
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/remover.vue"></script>
<script>
    remover.api.remove = '${remove}';
    remover.object = '${item.id}';
</script>
<table id="remover">
    <tr>
        <td>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="remove">
                <fmt:message key="button.remove"/>
            </button>
        </td>
    </tr>
</table>
