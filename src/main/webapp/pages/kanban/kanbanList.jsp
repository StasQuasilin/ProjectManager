<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.02.2020
  Time: 15:17
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/kanban.css"/>
<script src="${context}/vue/kanban/kanbanBoard.vue"></script>
<script src="${context}/vue/kanban/kanban.vue"></script>
<jsp:include page="../subscribePage.jsp"/>
    <script>
        kanban.api.saveTask = '${save}';
        kanban.api.removeTask = '${removeTask}';
        kanban.api.getSub = '${getSubTasks}';
        kanban.api.changeStatus = '${changeStatus}';
        <c:forEach items="${tasks}" var="task">
        kanban.todo.push(${task.task.toJson()});
        </c:forEach>
        <c:forEach items="${progressing}" var="t">
        kanban.inProgress.push(${t.toJson()});
        </c:forEach>
        subscribe(kanban);

    </script>
    <div id="kanban" style="height: 100%; width: 100%">
        <div class="border-holder">
            <board :color="'green'" :title="'TODO'" :items="todo" :status="'todo'"></board>
        </div>
        <div class="border-holder">
            <board :color="'green'" :title="'PROGRESSING'" :items="inProgress" :status="'processing'"></board>
        </div>
        <div class="border-holder">
            <board :color="'green'" :title="'DONE'" :items="done" :status="'done'" ></board>
        </div>
    </div>
</html>