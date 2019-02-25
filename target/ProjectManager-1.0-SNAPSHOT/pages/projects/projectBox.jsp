<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="${context}/ProjectBox.css">
</head>
<body>
  <div class="project-box">
    <div class="project-box-title" >
      <span id="project-title">Title</span>
    </div>
      <div class="progress-bar-background">
        <div class="progress-bar"></div>
      </div>
  </div>
</body>
</html>
