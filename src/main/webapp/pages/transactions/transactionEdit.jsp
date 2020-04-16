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
    <c:forEach items="${accounts}" var="budget">
    edit.budgets.push(${budget.toJson()});
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
    edit.transaction.budget = edit.budgets[0];
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
    }
    edit.counterpartyProps = {
        find:'${findCounterparty}',
        onPut:function(counterparty){
            edit.setCounterparty(counterparty);
        },
        field:'name'
    }
</script>
<html>
    <table id="editor">
        <tr>
            <td rowspan="2">
                <span>
                    <v-date-picker
                            :no-title="true"
                            class="date-picker"
                            :locale="locale"
                            :first-day-of-week="1"
                            v-model="transaction.date">
                    </v-date-picker>
                </span>
            </td>
            <td style="vertical-align: top">
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
                                <input id="sum" v-model="transaction.sum" onfocus="this.select()" style="border: none; background: transparent; outline: none">
                                <select v-model="transaction.currency">
                                    <option v-for="c in currencyList" :value="c.id">
                                        {{c.id}}
                                    </option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr v-if="transaction.currency !== transaction.budget.currency">
                        <td colspan="2">
                            <label for="rate">
                                <fmt:message key="currency.rate"/>
                            </label>
                            <input id="rate" v-model="transaction.rate" autocomplete="off" style="width: 7em">
                            {{(transaction.sum * transaction.rate).toLocaleString()}} {{transaction.budget.currency}}
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
                            <label for="budget">
                                <fmt:message key="payment.edit.budget"/>
                            </label>
                        </td>
                        <td>
                            <select id="budget" v-model="transaction.budget" style="width: 100%">
                                <option v-for="budget in budgets" :value="budget">
                                    {{budget.title}} ( {{budget.amount.toLocaleString()}} {{budget.currency}} )
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
                <div v-else-if="transaction.type === 'transfer'">
                    <table>
                        <tr>
                            <td>
                                <fmt:message key="transfer.from"/>
                            </td>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fmt:message key="transfer.to"/>
                            </td>
                            <td>

                            </td>
                        </tr>
                        <tr>
                            <td>
                                <fmt:message key="payment.edit.sum"/>
                            </td>
                        </tr>
                        <tr>

                        </tr>
                    </table>
                </div>
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
