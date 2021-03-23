<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 23:04
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/transactionEdit.css?v=${now}">
<script src="${context}/vue/templates/inputWithSearch.vue?v=${now}"></script>
<script src="${context}/vue/finances/transactions/transactionEdit.vue?v=${now}"></script>
<script>
  transactionEdit.api.findCounterparty = '${findCounterparty}';
  transactionEdit.api.save = '${save}';
  transactionEdit.api.remove = '${remove}';
  transactionEdit.newCurrencyProps.findCategory = '${findCurrency}';
  transactionEdit.props.findCategory = '${findCategory}';
  transactionEdit.detailProps.findCategory = '${findCategory}';
  transactionEdit.counterpartyProps.findCategory = '${findCounterparty}';
  transactionEdit.locale = '${locale}';
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
  transactionEdit.transaction = ${transaction.toJson()};
  if (transactionEdit.transaction.accountFrom){
    transactionEdit.transaction.accountFrom = transactionEdit.transaction.accountFrom.id;
  }
  if (transactionEdit.transaction.accountTo){
    transactionEdit.transaction.accountTo = transactionEdit.transaction.accountTo.id;
  }
  transactionEdit.transaction.amount = Math.abs(transactionEdit.transaction.amount);
  transactionEdit.transaction.details = [];
  <c:if test="${not empty details}">
  transactionEdit.useDetails = true;
  <c:forEach items="${details}" var="detail">
  transactionEdit.transaction.details.push(${detail.toJson()});
  </c:forEach>
  </c:if>
  </c:when>
  <c:otherwise>
  transactionEdit.transaction.type = transactionEdit.types[0].id;
  transactionEdit.transaction.accountFrom = transactionEdit.accounts[0].id;
  transactionEdit.transaction.currency = transactionEdit.currency[0];
  <c:if test="${not empty category}">
  transactionEdit.transaction.category = ${category.shortJson()}
  </c:if>
  </c:otherwise>
  </c:choose>
  if (!transactionEdit.transaction.counterparty){
    transactionEdit.transaction.counterparty = {
      id:-1,
      title:''
    }
  }
</script>
<table id="transactionEdit" v-if="transaction">
  <tr>
    <td>
      <v-date-picker
              :no-title="true"
              class="date-picker"
              :locale="locale"
              :first-day-of-week="1"
              v-model="transaction.date">
      </v-date-picker>
    </td>
    <td style="vertical-align: top">
      <div>
        <template v-for="type in types">
          <b v-if="transaction.type === type.id" class="transaction-type">
            {{type.name}}
          </b>
          <div v-else v-on:click="changeType(type.id)" class="transaction-type text-button">
            {{type.name}}
          </div>
        </template>
      </div>
      <div>
        <table class="full-size" border="1">
          <template v-if="transaction.type ==='spending' || transaction.type === 'income'">
            <tr>
              <td>
                <fmt:message key="transaction.category"/>
              </td>
              <td>
                <div style="display: inline-block">
                  <input-search :object="category" :props="props" :show="'title'" :create="true"></input-search>
                </div>
                  <span class="text-button">
                    &checkmark;
                  </span>
              </td>
            </tr>
<%--            <tr v-else>--%>
<%--              <td colspan="2">--%>
<%--                <span v-on:click="manyDetails = true" style="font-size: 10pt">--%>
<%--                  <fmt:message key="transaction.add.detail"/>--%>
<%--                </span>--%>
<%--              </td>--%>
<%--            </tr>--%>
            <tr>
              <td colspan="2">
                <div style="max-height: 100pt; overflow-y: scroll">
                  <div v-for="(d, idx) in transaction.details">
                    <template v-if="transaction.details.length > 1">
                    <span class="mini-button" v-on:click="removeDetail(idx)">
                      &times;
                    </span>
                      <span>
                      {{(idx + 1).toLocaleString()}}.
                    </span>
                    </template>
                    <span style="display: inline-block; width: 100pt" v-on:click="editDetail(idx)">
                    {{d.title}}
                  </span>
                    <span style="display: inline-block; width: 40pt">
                    <span v-on:click="changeAmount(idx, -1)">
                      &#9204;
                    </span>
                    <span>
                      {{d.amount}}
                    </span>
                    <span v-on:click="changeAmount(idx, 1)">
                      &#9205;
                    </span>
                  </span>
                    &times;
                    <input v-model="d.price" onfocus="this.select()" v-on:change="updateView()" autocomplete="off" style="width: 30pt">
                    <span v-if="d.amount > 1">
                    = {{(d.amount * d.price).toLocaleString()}}
                  </span>
                  </div>
                </div>
              </td>
            </tr>
          </template>
          <tr v-if="transaction.type === 'spending' || transaction.type === 'transfer'">
            <td>
              <label for="accountFrom">
                <fmt:message key="transaction.account.from"/>
              </label>
            </td>
            <td>
              <select id="accountFrom" v-model="transaction.accountFrom">
                <option v-for="account in accounts" :value="account.id">
                  {{account.title}} ( {{account.sum.toLocaleString()}} {{account.currency}} )
                </option>
              </select>
            </td>
          </tr>
          <tr v-if="transaction.type === 'income' || transaction.type === 'transfer'">
            <td>
              <label for="accountTo">
                <fmt:message key="transaction.account.to"/>
              </label>
            </td>
            <td>
              <select id="accountTo" v-model="transaction.accountTo">
                <option v-for="account in accounts" :value="account.id">
                  {{account.title}} ( {{account.sum.toLocaleString()}} {{account.currency}} )
                </option>
              </select>
            </td>
          </tr>
          <tr>
            <td>
                <fmt:message key="tranaction.sum"/>
            </td>
            <td>
              <template v-if="totalSum !== 0">
                <span v-if="transaction.type === 'spending'">
                  -
                </span>
                <span v-else-if="transaction.type === 'income'">
                  +
                </span>
              </template>
              <span v-if="transaction.type === 'income' || transaction.type === 'spending'">
                {{totalSum().toLocaleString()}}
              </span>
              <input type="number" v-else v-model="transaction.amount" autocomplete="off" onfocus="this.select()" style="width: 60pt">
              <template v-if="transaction.type === 'income' || transaction.type === 'spending'">
                <select v-model="transaction.currency" v-if="currency.length > 0">
                  <option v-for="c in currency" :value="c">
                    {{c}}
                  </option>
                </select>
                <span v-if="!addCurrency" class="text-button" style="font-size: 10pt" v-on:click="addCurrency = true">
                  + <fmt:message key="currency.add"/>
                </span>
                <input-search v-else :object="newCurrency" :props="newCurrencyProps" :show="null" :create="false"></input-search>
              </template>
              <span v-else-if="transaction.type === 'transfer'">
                {{accountsMap[transaction.accountTo].currency}}
              </span>
              <span v-if="showRate && transaction.rate !== 1" style="font-size: 10pt">
                &times; {{transaction.rate}} =
                {{(transaction.amount * transaction.rate).toLocaleString()}}
              </span>
            </td>
          </tr>
          <tr v-if="showRate">
            <td>
              <label for="rate">
                <fmt:message key="transaction.rate"/>
              </label>
            </td>
            <td>
              <input id="rate" v-model="transaction.rate" v-on:keyup="checkField('rate')"
                     autocomplete="off" onfocus="this.select()" style="width: 75pt">
              <span class="text-button" v-on:click="invertRate()">
                &#8617;
              </span>
            </td>
          </tr>
          <tr v-if="editCounterparty">
            <td>
              <fmt:message key="transaction.conterparty"/>
            </td>
            <td>
              <input-search :object="transaction.counterparty" :props="counterpartyProps"></input-search>
            </td>
          </tr>
          <tr v-if="!editNote || !useDetail || !editCounterparty">
            <td colspan="2" style="text-align: center; font-size: 10pt">
              <span class="text-button" v-if="!editNote">
                <fmt:message key="note.add"/>
              </span>
            </td>
          </tr>
        </table>
      </div>
    </td>
  </tr>
  <tr>
    <td colspan="3" style="text-align: center">
      <span class="text-button" v-if="transaction.id > 0" v-on:click="remove">
        <fmt:message key="button.remove"/>
      </span>
      <button onclick="closeModal()">
        <fmt:message key="button.cancel"/>
      </button>
      <button v-on:click="save()">
        <fmt:message key="button.save"/>
      </button>
    </td>
  </tr>
</table>
