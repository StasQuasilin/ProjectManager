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
            <tr v-if="transaction.details.length == 0 || manyDetails">
              <td>
                <fmt:message key="transaction.category"/>
              </td>
              <td>
                <input-search :object="transaction.category" :props="props" :width="'200pt'"></input-search>
              </td>
            </tr>
            <tr v-else>
              <td colspan="2">
                <span v-on:click="manyDetails = true">
                  <fmt:message key="transaction.add.detail"/>
                </span>
              </td>
            </tr>
            <tr>
              <td colspan="2">
                <div v-for="(d, idx) in transaction.details">
                  <template v-if="transaction.details.length > 1">
                    <span class="mini-button" v-on:click="removeDetail(idx)">
                      &times;
                    </span>
                    <span>
                      {{(idx + 1).toLocaleString()}}.
                    </span>
                  </template>
                  <span>
                    {{d.category.title}}
                  </span>
                  <span v-on:click="d.amount--">
                    <
                  </span>
                  <span>
                    {{d.amount}}
                  </span>
                  <span v-on:click="d.amount++">
                    >
                  </span>
                  &times;
                  <input v-model="d.price" onfocus="this.select()" autocomplete="off" style="width: 30pt">
                  <span v-if="d.amount > 1">
                    = {{(d.amount * d.price).toLocaleString()}}
                  </span>
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
              <input id="sum" type="number" step="0.01" v-model="transaction.amount" style="width: 90px;"
                     autocomplete="off" onfocus="this.select()">
              <select v-model="transaction.currency" v-if="transaction.type === 'income' || transaction.type === 'spending'">
                <option v-for="c in currency" :value="c">
                  {{c}}
                </option>
              </select>
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
    <td>
      <template v-if="(transaction.type === 'spending' || transaction.type === 'income') && useDetails">
        <div class="detail-header">
          <fmt:message key="transaction.details"/>
        </div>
        <div class="detail-list">
          <div v-for="(d, dIndex) in transaction.details" :key="d.id" class="detail-item" :class="{edited : dIndex === detailIndex}">
            <div class="detail-title">
              {{d.title}}
              <div class="detail-menu">
                <span class="text-button" v-on:click="deleteDetail(dIndex)">
                  &times;
                </span>
                <span class="text-button" v-on:click="editDetail(d, dIndex)">
                  &#9999;
                </span>
              </div>
            </div>
            <div style="font-size: 10pt; color: gray; padding-left: 16pt">
              {{d.amount}}&times;{{d.price}}
              <span v-if="d.amount > 1 && d.price > 0">
                = {{(d.amount * d.price).toLocaleString()}}
              </span>
              {{transaction.currency}}
            </div>
          </div>
        </div>
        <div style="font-size: 10pt">
          <input-search :object="detail" :props="detailProps" ></input-search>
          <label for="detailAmount">
            <fmt:message key="amount"/>
          </label>
          <input id="detailAmount" style="width: 40pt" v-model="detail.amount" v-on:keyup.enter="addDetail()"
                 autocomplete="off" onfocus="this.select()">
          <label for="price">
            <fmt:message key="transaction.price"/>
          </label>
          <input id="price" style="width: 50pt" v-model="detail.price" autocomplete="off"
                 onfocus="this.select()" v-on:keyup.enter="addDetail()">

          <button v-on:click="addDetail()">
            <fmt:message key="button.add"/>
          </button>
        </div>
      </template>
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
