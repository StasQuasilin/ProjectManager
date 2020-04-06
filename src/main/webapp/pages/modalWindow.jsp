<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/modal.css">
<div class="modal-content">
  <div>
    <div class="modal-title">
      <fmt:message key="${title}"/>
    </div>
  </div>
  <div>
    <jsp:include page="${pageContent}"/>
  </div>
</div>
</html>
