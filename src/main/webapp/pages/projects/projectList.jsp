<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <link rel="stylesheet" href="${context}/css/ProjectPage.css">
  <link rel="stylesheet" href="${context}/ProjectBox.css">
  <script src="${context}/vue/projects/projectList.vue"></script>
  <script>
    <c:forEach items="${subscribes}" var="subscribe">
    subscriber.subscribe('${subscribe}', projectList.update);
    </c:forEach>

  </script>
  <div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
    <button onclick="loadModal('${edit}')">
      <fmt:message key="project.new"/>
    </button>
  </div>
<div id="list" class="project-container">
  <div v-for="(value, key) in items" class="project-box">
    <div class="project-box-title">
      <span style="font-size: 14pt">{{value.title}}</span>
    </div>
    <div class="date-container">
      <span>
        <fmt:message key="project.edit.date-begin"/>: {{new Date(value.begin).toLocaleDateString()}}
      </span>
    </div>
    <div class="date-container">
      <span>
        <fmt:message key="project.edit.date-complete"/>: {{new Date(value.end).toLocaleDateString()}}
      </span>
    </div>
    <div class="budget-container">
      <span>
        <fmt:message key="project.budget"/>:
        <span v-if="value.budget">0/{{value.budget.amount}}</span>
        <span v-else>
          0
        </span>
      </span>
    </div>
    <div class="date-container">
      <span style="font-weight: bold">
        <fmt:message key="project.tasks"/>
      </span>
    </div>
    <div class="date-container">
      <span>
        <fmt:message key="project.tasks.active"/>:
        <span id="active-tasks">{{value.active}}</span>
      </span>
    </div>
    <div class="date-container">
      <span>
        <fmt:message key="project.tasks.canceled"/>:
        <span id="canceled-tasks">{{value.canceled}}</span>
      </span>
    </div>
    <div class="date-container">
      <span>
        <fmt:message key="project.tasks.done"/>:
        <span id="done-tasks">{{value.done}}</span>
      </span>
    </div>
    <div class="progress-bar-background">
      <div class="progress-bar" :style="{width:value.time + '%'}"></div>
    </div>
    <div class="progress-bar-background">
      <div class="progress-bar"></div>
    </div>
  </div>
</div>
</html>
