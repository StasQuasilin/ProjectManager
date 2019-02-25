<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ru"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <link rel="stylesheet" href="${context}/css/ProjectPage.css">
  <script src="${context}/js/projects/projectList.js"></script>
</head>
<body>
  <div>
    <span class="right-button" onclick="location.href='${context}/projects/edit.j'">
      <span style="background: transparent; font-size: 18pt;">+</span> <fmt:message key="projects.add"/>
    </span>
  </div>
  <div class="project-container" id="my-project-container"></div>
  <div class="project-title">
    <span>
      <fmt:message key="projects.my.other"/>
    </span>
  </div>
  <div class="project-container" id="other-project-container"></div>
</body>
</html>
<div id="box" style="display: none"><jsp:include page="projectBox.jsp"/></div>
