<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<div style="background: transparent; display: inline-block">
  <div id="title">
    <fmt:message key="${title}"/>
    <div style="float: right">
      ${user.person.surname} ${user.person.forename}
    </div>
  </div>
  <div id="filterShell" class="filter">
    <jsp:include page="${filter}"/>
  </div>
</div>
<jsp:include page="${pageContent}"/>
</html>
