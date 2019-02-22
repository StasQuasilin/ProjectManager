<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <link rel="stylesheet" href="css/Main.css">
    <title><fmt:message key="${title}"/></title>
    <script src="${context}/js/core.js"></script>
    <script>
        ctx = '${context}';
    </script>
</head>
<body>
<jsp:include page="${currentPage}"/>
</body>
</html>