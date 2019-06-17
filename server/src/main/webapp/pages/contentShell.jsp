<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<html>
<div id="title" style="background: transparent; display: inline-block">
  <fmt:message key="${title}"/>
</div>
<jsp:include page="${pageContent}"/>
</html>
