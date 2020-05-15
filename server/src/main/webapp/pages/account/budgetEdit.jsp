<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.02.2020
  Time: 11:43
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/accounts/accountEdit.vue"></script>
<script>

    edit.api.save = '${save}';
    <c:forEach items="${types}" var="type">
    edit.types.push({
        id:'${type}',
        name:'<fmt:message key="${type}"/>'
    });
    </c:forEach>
    <c:forEach items="${currency}" var="c">
    edit.currency.push(${c.currency.toJson()})
    </c:forEach>
    <c:choose>
    <c:when test="${not empty account}">
    edit.account = ${account.editorJson()}
    <c:if test="${not empty settings}">
    edit.depositSettings = ${settings.toJson()}
    </c:if>
    </c:when>
    <c:otherwise>
    edit.account.type = edit.types[0].id;
    // edit.currency.sort(function (a, b) {
    //     return a.main - b.main;
    // })
    edit.account.currency = edit.currency[0].id;
    </c:otherwise>
    </c:choose>
    <c:forEach items="${accounts}" var="account">
    edit.accounts.push(${account.toJson()});
    </c:forEach>
    edit.months[1] = '<fmt:message key="1.month"/>';
    edit.months[2] = '<fmt:message key="2.months"/>';
    edit.months[3] = '<fmt:message key="many.months"/>';
    edit.locale = '${language}';
</script>
<html>
<table id="editor" class="edit">
    <tr>
        <td colspan="2">
            <label for="name">
                <fmt:message key="account.name"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <input id="name" v-model="account.title" autocomplete="off" style="width: 100%">
        </td>
    </tr>
    <tr>
        <td>
            <label for="type">
                <fmt:message key="account.type"/>
            </label>
        </td>
        <td>
            <select id="type" v-model="account.type">
                <option v-for="t in types" :value="t.id">
                    {{t.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="sum">
                <fmt:message key="account.sum"/>
            </label>
        </td>
        <td>
            <input id="sum" type="number" step="100" v-model="account.amount " autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="currency">
                <fmt:message key="currency"/>
            </label>
        </td>
        <td>
            <select id="currency" v-model="account.currency">
                <option v-for="c in currency" :value="c.id">
                    {{c.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr v-if="account.type === 'card' || account.type === 'credit'">
        <td>
            <label for="limit">
                <fmt:message key="account.limit"/>
            </label>
        </td>
        <td>
            <input id="limit" type="number" step="1" v-model="account.limit" autocomplete="off">
        </td>
    </tr>
    <template v-else-if="account.type === 'deposit'">
        <tr>
            <td colspan="2" style="text-align: center">
                <fmt:message key="account.deposit.title"/>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="account.deposit.open"/>
            </td>
            <td>
                <span v-on:click="{date1 = !date1}">{{depositSettings.open}}</span>
                <div v-if="date1" style="position: absolute;" class="date-picker-holder">
                    <v-date-picker
                            @input="date1 = false"
                            :no-title="true"
                            class="date-picker"
                            :locale="locale"
                            :first-day-of-week="1"
                            v-model="depositSettings.open">
                    </v-date-picker>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="account.deposit.close"/>
            </td>
            <td>
                <span v-on:click="{date2 = !date2}">{{depositSettings.close}}</span>
                <div v-if="date2" style="position: absolute;" class="date-picker-holder">
                    <v-date-picker
                            @input="date2 = false"
                            :no-title="true"
                            class="date-picker"
                            :locale="locale"
                            :first-day-of-week="1"
                            v-model="depositSettings.close">
                    </v-date-picker>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <label for="bid">
                    <fmt:message key="account.deposit.bid"/>
                </label>
            </td>
            <td>
                <input id="bid" v-model="depositSettings.bid" type="number" step="0.01" autocomplete="off">
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <label for="period">
                    <fmt:message key="account.deposit.payment.period"/>
                </label>
                <select id="period" v-model="depositSettings.payment" style="display: inline-block; width: auto">
                    <option v-for="i in 12" :value="i">
                        {{i}}
                    </option>
                </select>
                <span v-if="depositSettings.payment == 1">
                    {{months[1].toLowerCase()}}
                </span>
                <span v-else-if="depositSettings.payment >= 2 && depositSettings.payment <= 4">
                    {{months[2].toLowerCase()}}
                </span>
                <span v-else>
                    {{months[3].toLowerCase()}}
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <label for="paymentAccount">
                    <fmt:message key="account.deposit.payment.account"/>
                </label>
            </td>
            <td>
                <select id="paymentAccount" style="width: 100%" v-model="depositSettings.account">
                    <option value="0">
                        <fmt:message key="account.deposit.payment.self"/>
                    </option>
                    <option v-for="acc in accounts" :value="account.id">
                        {{acc.title}}
                    </option>
                </select>
            </td>
        </tr>
    </template>
    <tr>
        <td colspan="2" align="center">
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
