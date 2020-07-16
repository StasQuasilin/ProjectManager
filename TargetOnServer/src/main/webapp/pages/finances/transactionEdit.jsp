<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 23:04
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<link rel="stylesheet" href="${context}/css/transactionEdit.css">
<script src="${context}/vue/templates/inputWithSearch.vue"></script>
<script src="${context}/vue/finances/transactionEdit.vue"></script>
<script>
  transactionEdit.props.findCategory = '${findCategory}';
  transactionEdit.api.findCounterparty = '${findCounterparty}';
  transactionEdit.api.save = '${save}';
  <c:forEach items="${types}" var="type">
  transactionEdit.types.push({
    id:'${type}',
    name:'<fmt:message key="${type}"/>'
  });
  </c:forEach>
  <c:forEach items="${currency}" var="c">
  transactionEdit.currency.push('${c}');
  </c:forEach>
  <c:forEach items="${accounts}" var="account">
  transactionEdit.accounts.push(${account.toJson()});
  </c:forEach>
  <c:choose>
  <c:when test="${not empty transaction}">
  transactionEdit.transaction = ${transaction.toJson()}
  </c:when>
  <c:otherwise>
  transactionEdit.transaction.type = transactionEdit.types[0].id;
  transactionEdit.transaction.accountFrom = transactionEdit.accounts[0];
  transactionEdit.transaction.currency = transactionEdit.currency[0];
  </c:otherwise>
  </c:choose>

</script>
<table id="transactionEdit">
  <tr>
    <td>
      Календурік
    </td>
    <td>
      <div>
        <template v-for="type in types">
          <b v-if="transaction.type === type.id" class="transaction-type">
            {{type.name}}
          </b>
          <div v-else v-on:click="transaction.type = type.id" class="transaction-type text-button">
            {{type.name}}
          </div>
        </template>
      </div>
      <div>
        <table class="full-size">
          <tr v-if="transaction.type ==='spending' || transaction.type === 'income'">
            <td>
              <fmt:message key="transaction.category"/>
            </td>
            <td>
              <input-search :object="transaction.category" :props="props"></input-search>
            </td>
          </tr>
          <tr>
            <td>
              <label for="accountFrom">
                <fmt:message key="transaction.account.from"/>
              </label>
            </td>
            <td>
              <select id="accountFrom" v-model="transaction.accountFrom.id">
                <option v-for="account in accounts" :value="account.id">
                  {{account.title}} ( {{account.sum}} {{account.currency}} )
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
              <label for="sum">
                <fmt:message key="tranaction.sum"/>
              </label>
            </td>
            <td>
              <span v-if="transaction.type === 'spending'">
                -
              </span>
              <span v-else-if="transaction.type === 'income'">
                +
              </span>
              <input id="sum" v-model="transaction.amount" v-on:keyup="checkField('amount')" style="width: 90px;"
                     autocomplete="off" onfocus="this.select()">
              <select v-model="transaction.currency">
                <option v-for="c in currency" :value="c">
                  {{c}}
                </option>
              </select>
              <span v-if="transaction.rate !== 1">
                &times; {{transaction.rate}} =
                {{(transaction.amount * transaction.rate).toLocaleString()}}
                {{transaction.accountFrom.currency}}
              </span>
            </td>
          </tr>
          <tr v-if="transaction.accountFrom.currency !== transaction.currency">
            <td colspan="2">
              <label for="rate">
                <fmt:message key="transaction.rate"/>
              </label>
              <input id="rate" v-model="transaction.rate" v-on:keyup="checkField('rate')"
                     autocomplete="off" onfocus="this.select()" style="width: 75pt">
            </td>
          </tr>
        </table>
      </div>
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
