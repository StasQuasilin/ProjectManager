<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
<script src="${context}/js/payments/paymentEdit.js"></script>
<script>
<c:forEach items="${paymentTypes}" var="pt">
edit.types.push({
  id:'${pt}',
  name:'<fmt:message key="payment.${pt}"/>'
});
edit.payment.type='${type}';
</c:forEach>
<c:forEach items="${budgets}" var="account">
edit.budgets.push({
  id:${account.id},
  name:'${account.title}'
});
</c:forEach>
</script>
<div id="editor">
  <table>
    <tr>
      <td colspan="2">
        <input type="radio" name="type" v-model="payment.type" id="income" :value="'income'">
        <label for="income">
          <fmt:message key="payment.income"/>
        </label>
        <input type="radio" name="type" v-model="payment.type" id="expanse" :value="'expense'">
        <label for="expanse">
          <fmt:message key="payment.expense"/>
        </label>
      </td>
    </tr>
    <tr>
      <td colspan="2">
        {{payment.type}}
      </td>
    </tr>
    <tr>
      <td>
        <label for="sum">
          <fmt:message key="payment.edit.sum"/>
        </label>
      </td>
      <td>
        <span v-if="payment.type == 'income'">
          +
        </span>
        <span v-else-if="payment.type == 'expense'">
          -
        </span>
        <input id="sum" type="number" step="0.01">
      </td>
    </tr>
    <tr>
      <td>
        <label for="category">
          <fmt:message key="payment.edit.category"/>
        </label>
      </td>
      <td>
        <input id="category">
      </td>
    </tr>
    <tr>
      <td>
        <label for="account">
          <fmt:message key="payment.edit.account"/>
        </label>
      </td>
      <td>
        <select id="account" style="width: 100%">
          <option v-for="account in budgets" :value="account.id">
            {{account.name}}
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
</div>

</html>
