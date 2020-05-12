<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
  <div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
    <c:forEach items="${paymentTypes}" var="pt">
      <button onclick="loadModal('${edit}?type=${pt}')">
        <fmt:message key="payment.${pt}"/>
      </button>
    </c:forEach>
  </div>
</html>
