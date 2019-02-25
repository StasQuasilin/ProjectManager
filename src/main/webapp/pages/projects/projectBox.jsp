<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value=""/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>

</head>
<body>
  <div class="project-box">
    <div class="project-box-container">
      <div class="project-box-title" >
        <span id="project-title" style="font-size: 14pt">Title</span>
      </div>
      <div class="date-container">
        <span>
          <fmt:message key="project.edit.date-begin"/>:
        </span>
        <span id="project-begin-date"></span>
      </div>
      <div class="date-container">
        <span>
          <fmt:message key="project.edit.date-complete"/>:
        </span>
        <span id="project-complete-date"></span>
      </div>
      <div class="budget-container" style="visibility: hidden">
        <span>
          <fmt:message key="project.budget"/>:
        </span>
        <span id="current-budget">0</span>
        /
        <span id="target-budget">200</span>
        <span id="budget-currency">uah</span>
      </div>
      <div class="date-container">
        <span style="font-weight: bold">
          <fmt:message key="project.tasks"/>
        </span>
      </div>
      <div class="date-container">
        <span>
          <fmt:message key="project.tasks.active"/>:
          <span id="active-tasks">0</span>
        </span>
      </div>
      <div class="date-container">
        <span>
          <fmt:message key="project.tasks.canceled"/>:
          <span id="canceled-tasks">0</span>
        </span>
      </div>
      <div class="date-container">
        <span>
          <fmt:message key="project.tasks.done"/>:
          <span id="done-tasks">0</span>
        </span>
      </div>
      <div class="progress-bar-background">
        <div class="progress-bar"></div>
      </div>
    </div>

  </div>
</body>
</html>
