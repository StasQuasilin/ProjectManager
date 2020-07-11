<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:27
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${context}/vue/finances/transactionsList.vue"></script>
<script src="${context}/vue/finances/fastTransactions.vue"></script>
<script src="${context}/vue/finances/accounts.vue"></script>
<script src="${context}/vue/finances/buyList.vue"></script>
<script>
  transactionsList.api.edit = '${transactionEdit}';
  fastTransactions.api.edit = '${fastTransactionEdit}';
  accounts.api.edit = '${accountEdit}';
  buyList.api.edit = '${buyListEdit}';
  subscriber.subscribe('${transactionSubscribe}', transactionsList.handler);
  subscriber.subscribe('${accountSubscribe}', accounts.handler);
</script>
<table class="full-size" border="1">
  <tr>
    <td rowspan="2" style="width: 40%">
      <jsp:include page="transactions.jsp"/>
    </td>
    <td style="width: 30%">
      <jsp:include page="fastTransactions.jsp"/>
    </td>
    <td rowspan="2" style="width: 30%;">
      <jsp:include page="buyList.jsp"/>
    </td>
  </tr>
  <tr>
    <td>
      <jsp:include page="accounts.jsp"/>
    </td>
  </tr>
</table>
