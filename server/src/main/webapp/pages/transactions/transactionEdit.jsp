<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 26.02.2020
  Time: 15:41
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/transactions/transactionEdit.css">
<script src="${context}/vue/templates/findInput.vue"></script>
<script src="${context}/vue/transactions/transactionEdit.vue"></script>
<script>
    edit.api.save = '${save}';
    edit.api.findCategory = '${findCategory}';
    <c:forEach items="${types}" var="type">
    edit.types.push({
        id:'${type}',
        name:'<fmt:message key="transaction.${type}"/>'
    });
    </c:forEach>
    <c:forEach items="${accounts}" var="account">
    edit.budgets.push(${account.toJson()});
    </c:forEach>
    <c:choose>
    <c:when test="${not empty transaction}">
    edit.transaction = ${transaction.toJson()};
    edit.transaction.sum = Math.abs(edit.transaction.sum);
    if (!edit.transaction.counterparty){
        edit.transaction.counterparty = {
            id:-1,
            name:''
        }
    } else {
        edit.addCounterparty = true;
    }
    if (edit.transaction.comment){
        edit.addNote = true;
    }
    </c:when>
    <c:otherwise>
    edit.transaction.type = edit.types[0].id;
    edit.transaction.account = edit.budgets[0];
    </c:otherwise>
    </c:choose>
    <c:forEach items="${currency}" var="c">
    edit.currencyList.push(${c.currency.toJson()});
    </c:forEach>
    edit.transaction.currency = edit.currencyList[0].id;
    edit.locale='${language}';
    edit.props = {
        find:'${findCategory}',
        onPut:function(item){
            edit.setCategory(item);
        },
        field:'name'
    };
    edit.counterpartyProps = {
        find:'${findCounterparty}',
        onPut:function(counterparty){
            edit.setCounterparty(counterparty);
        },
        field:'name'
    };
    <c:if test="${not empty dateSelect}">
    edit.selectDate = ${dateSelect};
    </c:if>
</script>
<html>
    <table id="editor">
        <tr>
            <td rowspan="2" v-if="selectDate">
                <div>
                    <v-date-picker
                            :no-title="true"
                            class="date-picker"
                            :locale="locale"
                            :first-day-of-week="1"
                            v-model="transaction.date">
                    </v-date-picker>
                </div>
            </td>
            <td style="vertical-align: top; width: 314px">
                <div>
                    <template v-for="t in types">
                        <b v-if="t.id == transaction.type">
                            {{t.name}}
                        </b>
                        <button v-else v-on:click="transaction.type = t.id">
                            {{t.name}}
                        </button>
                    </template>
                </div>
<%--                TRANSACTIONS--%>
                <table v-if="transaction.type === 'income' || transaction.type === 'outcome'">
                    <tr>
                        <td>
                            <label for="sum">
                                <fmt:message key="payment.edit.sum"/>
                            </label>
                        </td>
                        <td>
                            <div style="display: inline-block; border: solid black 1pt; background-color: white; padding: 0 2pt">
                            <span class="sign" v-if="transaction.type=='income'">
                                +
                            </span>
                            <span class="sign" v-else>
                                -
                            </span>
                                <input id="sum" v-model.number="transaction.sum" autocomplete="off"
                                       onfocus="this.select()" style="border: none; background: transparent; outline: none">
                                <select v-model="transaction.currency" style="width: 55px">
                                    <option v-for="c in currencyList" :value="c.id">
                                        {{c.id}}
                                    </option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td v-if="!addDetails" colspan="2" style="text-align: center">
                            <span class="mini-button" v-on:click="addDetails = true">
<%--                                <fmt:message key="transaction.details.add"/>--%>
                                Add details
                            </span>
                        </td>
                        <td v-else>
                            <div>
                                <span>
<%--                                <fmt:message key="transaction.details"/>--%>
                                    Details
                                </span>
                                <span class="mini-button" v-on:click="closeDetails()">
                                    &times;
                                </span>
                            </div>
                            <div>
                                <div v-for="detail in transaction.details">
                                    {{detail}}
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr v-if="transaction.currency !== transaction.account.currency.id">
                        <td colspan="2">
                            <label for="rate">
                                <fmt:message key="currency.rate"/>
                            </label>
                            <input id="rate" v-model="transaction.rate" autocomplete="off" style="width: 7em">
                            {{(transaction.sum * transaction.rate).toLocaleString()}} {{transaction.account.currency.id}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label>
                                <fmt:message key="payment.edit.category"/>
                            </label>
                        </td>
                        <td>
                            <find :props="props" :object="transaction.category"></find>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="account">
                                <fmt:message key="payment.edit.account"/>
                            </label>
                        </td>
                        <td>
                            <select id="account" v-model="transaction.account" style="width: 100%">
                                <option v-for="account in budgets" :value="account">
                                    {{account.title}} ( {{account.amount.toLocaleString()}} {{account.currency.id}} )
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr v-if="addCounterparty">
                        <td>
                            <span v-if="transaction.type === 'income'">
                                <fmt:message key="transaction.payeer"/>
                            </span>
                            <span v-else>
                                <fmt:message key="transaction.recepient"/>
                            </span>
                        </td>
                        <td>
                            <find :props="counterpartyProps" :object="transaction.counterparty"></find>
                            <span v-on:click="addCounterparty = false" class="mini-button">
                                &times;
                            </span>
                        </td>
                    </tr>
                    <tr v-if="addNote">
                        <td colspan="2">
                            <textarea onfocus="this.select()" class="comment-field"
                                      v-model="transaction.comment"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <span class="mini-button" v-if="!addCounterparty" v-on:click="addCounterparty = true">
                                <template v-if="transaction.type == 'income'">
                                    <fmt:message key="transacton.payeer.add"/>
                                </template>
                                <template v-else-if="transaction.type == 'outcome'">
                                    <fmt:message key="transaction.recepient.add"/>
                                </template>
                            </span>
                            <span class="mini-button" v-if="!addNote" v-on:click="addNote = true">
                                <fmt:message key="transaction.note.add"/>
                            </span>
                        </td>
                    </tr>
                </table>
<%--                TRANSFERS--%>
                <table v-else-if="transaction.type === 'transfer'">
                    <tr>
                        <td>
                            <label for="from">
                                <fmt:message key="transfer.from"/>
                            </label>
                        </td>
                        <td>
                            <select id="from" v-model="transaction.secondary">
                                <option v-for="acc in budgets" :value="acc">
                                    {{acc.title}} {{acc.currency.sign}}
                                </option>
                            </select>
                            -{{transaction.sum}}
                        </td>
                    </tr>
<%--                    <tr v-if="transaction.account.currency.id !== transaction.transferTo.currency.id">--%>
<%--                        <td>--%>
<%--                            <label for="rate">--%>
<%--                                <fmt:message key="currency.rate"/>--%>
<%--                            </label>--%>
<%--                        </td>--%>
<%--                        <td>--%>
<%--                            <input id="rate" v-model="transaction.rate" autocomplete="off">--%>
<%--                        </td>--%>
<%--                    </tr>--%>
                    <tr>
                        <td>
                            <label for="to">
                                <fmt:message key="transfer.to"/>
                            </label>
                        </td>
                        <td>
                            <select id="to" v-model="transaction.account">
                                <option v-for="acc in budgets" :value="acc">
                                    {{acc.title}} {{acc.currency.sign}}
                                </option>
                            </select>
                            +{{transaction.sum}}
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <label for="transferSum">
                                <fmt:message key="payment.edit.sum"/>
                            </label>
                        </td>
                        <td>
                            <input id="transferSum" v-model="transaction.sum" autocomplete="off" onfocus="this.select()">
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center">
                            <span class="mini-button" v-if="!addNote" v-on:click="addNote = true">
                                <fmt:message key="transaction.note.add"/>
                            </span>
                            <textarea v-else onfocus="this.select()" class="comment-field"
                                v-model="transaction.comment"></textarea>
                        </td>
                    </tr>
                </table>
<%--                DEBT--%>
                <div v-else>
                    DEBT
                </div>
            </td>
        </tr>
        <tr>
            <td style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="buttons.cancel"/>
                </button>
                <button v-on:click="save">
                    <fmt:message key="buttons.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
