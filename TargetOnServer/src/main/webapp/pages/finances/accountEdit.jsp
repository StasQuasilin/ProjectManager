<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 08.07.2020
  Time: 11:00
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/finances/accountEdit.vue"></script>
<script>
  accountEdit.api.save = '${save}';
  <c:forEach items="${types}" var="type">
  accountEdit.types.push('${type}');
  </c:forEach>
  <c:forEach items="${currency}" var="c">
  accountEdit.currency.push('${c}');
  </c:forEach>
  <c:choose>
  <c:when test="${not empty account}">
  accountEdit.account = ${account.toJson()};
  </c:when>
  <c:otherwise>
  accountEdit.account.type = accountEdit.types[0];
  accountEdit.account.currency = accountEdit.currency[0];
  </c:otherwise>
  </c:choose>

</script>
<table id="accountEdit">
  <tr>
    <td colspan="2">
      <label for="accountName">
        <fmt:message key="account.name"/>
      </label>
    </td>
  </tr>
  <tr>
    <td colspan="2">
      <input id="accountName" v-model="account.title" autocomplete="off" onfocus="this.select()">
    </td>
  </tr>
  <tr>
    <td>
      <label for="accountType">
        <fmt:message key="account.type"/>
      </label>
    </td>
    <td>
      <select id="accountType" v-model="account.type">
        <option v-for="type in types" :value="type">
          {{type}}
        </option>
      </select>
    </td>
  </tr>
  <tr>
    <td>
      <label for="accountAmount">
        <fmt:message key="account.amount"/>
      </label>
    </td>
    <td>
      <input id="accountAmount" v-model.number="account.sum" style="width: 90px" autocomplete="off" onfocus="this.select()">
      <select v-model="account.currency">
        <option v-for="c in currency" :value="c">
          {{c}}
        </option>
      </select>
    </td>
  </tr>
  <tr v-if="account.type === 'card' || account.type === 'credit'">
    <td>
      <label for="accountLimit">
        <fmt:message key="account.limit"/>
      </label>
    </td>
    <td>
      <input id="accountLimit" v-model="account.limit" style="width: 90px"
             autocomplete="off" onfocus="this.select()">

      {{account.currency}}
    </td>
  </tr>
  <tr>
    <td colspan="2" style="text-align: center">
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="save()">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
