<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:27
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/finances/financePage.css">
<script src="${context}/vue/pathBuilder.vue"></script>
<script src="${context}/vue/finances/transactions/transactionsList.vue?v=${now}"></script>
<script src="${context}/vue/finances/fastTransactions.vue?v=${now}"></script>
<script src="${context}/vue/finances/accounts.vue?v=${now}"></script>
<script src="${context}/vue/finances/buyList.vue?v=${now}"></script>
<script>
  transactionsList.api.edit = '${transactionEdit}';
  transactionsList.limit = ${transactionLimit};
  fastTransactions.api.edit = '${fastTransactionEdit}';
  accounts.api.edit = '${accountEdit}';
  accounts.api.extract = '${accountExtract}';
  buyList.api.edit = '${buyListEdit}';
  buyList.api.changeStatus = '${transactionEdit}';
  <c:forEach items="${units}" var="unit">
  buyList.unitNames['${unit}'] = '<fmt:message key="unit.${unit}"/>';
  </c:forEach>
  subscriber.subscribe('${transactionSubscribe}', transactionsList.handler);
  subscriber.subscribe('${accountSubscribe}', accounts.handler);
  subscriber.subscribe('${buyListSubscribe}', buyList.handler);

</script>
<div id="titleContent" style="display: inline-block">
  <span class="text-button" onclick="loadPage('${categories}')">
    <fmt:message key="menu.categories"/>
  </span>
</div>
<div class="full-size finance-page">
  <div class="colon">
    <div class="colon-cell">
      <jsp:include page="transactions.jsp"/>
    </div>
  </div>
  <div class="colon central">
<%--    <div class="colon-cell">--%>
<%--      <jsp:include page="fastTransactions.jsp"/>--%>
<%--    </div>--%>
    <div class="colon-cell">
      <jsp:include page="accounts.jsp"/>
    </div>
  </div>
  <div class="colon colon-cell">
    <jsp:include page="buyList/buyList.jsp"/>
  </div>
</div>
