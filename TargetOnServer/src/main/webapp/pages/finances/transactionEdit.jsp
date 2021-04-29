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
<script src="${context}/vue/templates/categoryInput.vue?v=${now}"></script>
<script src="${context}/vue/finances/transactions/transactionEdit.vue?v=${now}"></script>
<script>
  transactionEdit.api.findCounterparty = '${findCounterparty}';
  transactionEdit.api.save = '${save}';
  transactionEdit.api.remove = '${remove}';
  transactionEdit.api.rate = '${exchangeRate}';
  transactionEdit.newCurrencyProps.findCategory = '${findCurrency}';
  transactionEdit.props.findCategory = '${findCategory}';
  transactionEdit.props.categoryTitle = '<fmt:message key="transaction.category.insert"/>';
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
  transactionEdit.initTransaction();

<%--  <c:if test="${not empty category}">--%>
<%--  transactionEdit.transaction.category = ${category.shortJson()}--%>
<%--  </c:if>--%>
  </c:otherwise>
  </c:choose>
</script>
<table id="transactionEdit" v-if="transaction">
  <tr>
    <td>
      <v-date-picker :no-title="true" class="date-picker" :locale="locale"
              :first-day-of-week="1" v-model="transaction.date"></v-date-picker>
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
        <table class="full-size transaction-table">
          <template v-if="transaction.type ==='spending' || transaction.type === 'income'">
            <tr>
              <td colspan="2">
                <div style="width: 100%; display: flex">
                  <div style="display: inline-block; width: 100%;">
                    <category-input :object="category" :props="props" :show="['title']" :create="true"></category-input>
                  </div>
                  <span class="text-button">
                  &checkmark;
                </span>
                  <div class="tips">
                    <div class="tips-content">
                      <p>
                        <fmt:message key="transaction.tips.1"/>
                      </p>
                      <p>
                        <fmt:message key="transaction.tips.2"/>
                      </p>
                    </div>
                  </div>
                </div>
              </td>
            </tr>
            <tr v-if="transaction.details.length > 0">
              <td colspan="2">
                <div class="transaction-detail-list">
                  <div v-for="(d, idx) in transaction.details">
                    <span class="mini-button" v-on:click="removeDetail(idx)">
                      &times;
                    </span>
                    <span>
                      {{(idx + 1).toLocaleString()}}.
                    </span>
                    <span style="display: inline-block; width: 200pt" v-on:click="editDetail(idx)">
                      <template v-if="d.path.length">
                        <span style="font-size: 8pt" v-for="p in d.path">{{p.title}} / </span><br>
                      </template>
                      {{d.title}}
                    </span>
<%--                    <template v-if="d.children">--%>
<%--                      <select>--%>
<%--                        <option v-for="c in d.children">--%>
<%--                          {{c.title}}--%>
<%--                        </option>--%>
<%--                      </select>--%>
<%--                    </template>--%>
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
          <tr v-if="showTempCategory">
            <td colspan="2">
              <label for="temp">
                {{tempCategory.title}} /
              </label>
              <select id="temp">
                <option v-for="c in tempCategory.children">
                  {{c.title}}
                </option>
              </select>
            </td>
          </tr>
          <tr v-if="transaction.type === 'spending' || transaction.type === 'transfer' || (transaction.type === 'debt' && !lend)">
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
          <tr v-if="transaction.type === 'income' || transaction.type === 'transfer' || (transaction.type === 'debt' && lend)">
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
              <template v-if="transaction.type === 'income' || transaction.type === 'spending' || transaction.type === 'transfer'">
                <fmt:message key="tranaction.sum"/>
              </template>
              <span class="text-button" v-else v-on:click="lend=!lend">
                <template v-if="lend">
                  <fmt:message key="tranaction.debt.credit"/>
                </template>
                <template v-else>
                  <fmt:message key="tranaction.debt.debit"/>
                </template>
              </span>
            </td>
            <td>
              <span v-if="transaction.type === 'income' || transaction.type === 'spending'"
                    style="width: 50pt; display: inline-block; text-align: right;">
                <template v-if="totalSum !== 0">
                  <template v-if="transaction.type === 'spending'">-</template>
                  <template v-else-if="transaction.type === 'income'">+</template>
                </template>
                {{totalSum.toLocaleString()}}
              </span>
              <input type="number" step="0.001" v-else v-model="transaction.amount" autocomplete="off" onfocus="this.select()" style="width: 60pt">
              <template v-if="transaction.type === 'income' || transaction.type === 'spending' || transaction.type === 'debt'">
                <select v-model="transaction.currency" v-if="currency.length > 0">
                  <option v-for="c in currency" :value="c">
                    {{c}}
                  </option>
                </select>
              </template>
              <span style="font-size: 9pt">
                <template v-if="showRate && transaction.rate !== 1">
                  <template v-if="transaction.type === 'transfer'">
                    {{accountsMap[transaction.accountFrom].currency}}
                  </template>
                  &times; {{transaction.rate}} =
                  {{(totalSum * transaction.rate).toLocaleString()}}
                </template>
                <template v-if="transaction.type === 'transfer' || transaction.type === 'income'">
                  {{accountsMap[transaction.accountTo].currency}}
                </template>
                <template v-else-if="transaction.type === 'spending'">
                  {{accountsMap[transaction.accountFrom].currency}}
                </template>
              </span>
            </td>
          </tr>
          <tr>
            <td colspan="2" style="text-align: right">
              <span v-if="!addCurrency" class="text-button" style="font-size: 10pt" v-on:click="addCurrency = true">
                  +<fmt:message key="currency.add"/>
              </span>
              <div v-else style="display: flex">
                <fmt:message key="currency.add.text"/>
                <input-search :object="newCurrency" :props="newCurrencyProps" :show="null" :create="false" :width="50"></input-search>
                <span class="text-button" v-on:click="addCurrency=false">
                  &times;
                </span>
              </div>
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
          <tr v-if="editCounterparty || transaction.type === 'debt'">
            <td>
              <fmt:message key="transaction.conterparty"/>
            </td>
            <td>
              <input-search :object="counterpartyInput" :props="counterpartyProps" :show="['title']" :create="true"></input-search>
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
