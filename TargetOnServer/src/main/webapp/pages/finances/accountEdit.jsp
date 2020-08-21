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
<script type="application/javascript" src="${context}/vue/templates/datetime/datePicker.vue"></script>
<script type="application/javascript" src="${context}/vue/finances/accountEdit.vue"></script>
<script type="application/javascript">
  accountEdit.api.save = '${save}';
  <c:forEach items="${types}" var="type">
  accountEdit.types.push('${type}');
  accountEdit.typeNames['${type}'] = '<fmt:message key="account.${type}"/>';
  </c:forEach>
  <c:forEach items="${currency}" var="c">
  accountEdit.currency.push('${c}');
  </c:forEach>
  <c:choose>
  <c:when test="${not empty account}">
  accountEdit.account = ${account.toJson()};
  <c:if test="${not empty depositSettings}">
  accountEdit.account.settings = ${depositSettings.toJson()}
  </c:if>
  </c:when>
  <c:otherwise>
  accountEdit.account.type = accountEdit.types[0];
  accountEdit.account.currency = accountEdit.currency[0];
  </c:otherwise>
  </c:choose>
  if(accountEdit.account.type === 'deposit'){
    if (!accountEdit.account.settings){
      accountEdit.account.settings = {
        open:new Date().toISOString().substring(0, 10),
        period:30,
        rate:12,
        capitalization:false
      }
    }
  }
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
      <select id="accountType" v-model="account.type" style="width: 100%" v-on:change="checkType()">
        <option v-for="type in types" :value="type">
          {{typeNames[type]}}
        </option>
      </select>
    </td>
  </tr>
  <tr v-if="account.type !== 'credit'">
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
  <template v-if="account.type === 'card' && account.limit > 0">
    <tr>
      <td colspan="2" style="text-align: right">
        <input id="remember" type="checkbox" v-model="account.settings.remember">
        <label for="remember">
          <fmt:message key="card.settings.remember"/>
        </label>
      </td>
    </tr>
    <tr>
      <td>
        <label for="exemption">
          <fmt:message key="card.exemption.period"/>
        </label>
      </td>
      <td>
        <input id="exemption" v-model="account.settings.exemption" type="number" style="width: 90px">
        <fmt:message key="5.days"/>
      </td>
    </tr>
  </template>
  <template v-if="account.type === 'deposit'">
    <tr>
      <td colspan="2" style="text-align: center">
        <fmt:message key="deposit.settings"/>
      </td>
    </tr>
    <tr>
      <td>
        <fmt:message key="deposit.date"/>
      </td>
      <td>
        <date-picker :date="account.settings.open" :props="depositDateProps"></date-picker>
      </td>
    </tr>
    <tr>
      <td>
        <label for="period">
          <fmt:message key="deposit.period"/>
        </label>
      </td>
      <td>
        <input id="period" type="number" step="1" v-model="account.settings.period"
               onfocus="this.select()" style="width: 90px">
      </td>
    </tr>
    <tr>
      <td>
        <fmt:message key="deposit.close"/>
      </td>
      <td>
        {{datePlusDays(account.settings.open, account.settings.period).toLocaleDateString()}}
      </td>
    </tr>
    <tr>
      <td>
        <label for="rate">
          <fmt:message key="deposit.rate"/>
        </label>
      </td>
      <td>
        <input id="rate" v-model="account.settings.rate" type="number" step="0.01" autocomplete="off"
               onfocus="this.select()" style="width: 90px"> %
      </td>
    </tr>
    <tr>
      <td colspan="2" style="text-align: right">
        <input id="capitalization" v-model="account.settings.capitalization" type="checkbox">
        <label for="capitalization">
          <fmt:message key="deposit.capitalization"/>
        </label>
      </td>
    </tr>
  </template>
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
