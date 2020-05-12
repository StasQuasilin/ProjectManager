<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 01.04.20
  Time: 16:03
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/vue/templates/findInput.vue"></script>
<script src="${context}/vue/transactions/transactionSettings.vue"></script>
<script>
    <c:forEach items="${repeats}" var="repeat">
    edit.repeats.push({
        id:'${repeat}',
        title:'<fmt:message key="${repeat}.name"/>'
    });
    </c:forEach>
    <c:forEach items="${budgets}" var="budget">
    edit.budgets.push(${budget.toJson()});
    </c:forEach>
    <c:forEach items="${currency}" var="c">
    edit.currency.push(${c.toJson()});
    </c:forEach>
</script>
    <table id="editor">
        <tr>
            <td>
                <fmt:message key="transaction.settings.date.from"/>
            </td>
            <td>
                00.00.0000
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <span>
                    <fmt:message key="transaction.settings.date.to.add"/>
                </span>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="transaction.settings.repeat"/>
            </td>
            <td>
                <select>
                    <option v-for="repeat in repeats" :value="repeat.id">
                        {{repeat.title}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="payment.edit.budget"/>
            </td>
            <td>
                <select>
                    <option v-for="budget in budgets" :value="budget.id">
                        {{budget.title}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="payment.edit.category"/>
            </td>
            <td>
                <find></find>
            </td>
        </tr>
        <tr>
            <td>
                <label for="sum">
                    <fmt:message key="payment.edit.sum"/>
                </label>
            </td>
            <td>
                <input id="sum">
                <select>
                    <option v-for="c in currency" :value="c.currency.id">
                        {{c.currency.sign}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2" align="center">
                <button onclick="closeModal()">
                    <fmt:message key="buttons.cancel"/>
                </button>
                <button>
                    <fmt:message key="buttons.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
