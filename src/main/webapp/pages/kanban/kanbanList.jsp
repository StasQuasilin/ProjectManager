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
        var props = {
            onSave:function(a, b){
                kanban.drop(a, b);
            }
        };
        kanban.todoProps = Object.assign({}, props);
        kanban.todoProps.title= '<fmt:message key="kanban.todo"/>';
        kanban.processingProps = Object.assign({}, props);
        kanban.processingProps.title= '<fmt:message key="kanban.processing"/>';
        kanban.doneProps = Object.assign({}, props);
        kanban.doneProps.title= '<fmt:message key="kanban.done"/>';

    </script>
    <div id="kanban" style="height: 100%; width: 100%">
        <div class="border-holder">
            <board :color="'green'" :items="todo" :status="'todo'" :props="todoProps"></board>
        </div>
        <div class="border-holder">
            <board :color="'green'" :items="inProgress" :status="'progressing'" :props="processingProps"></board>
        </div>
        <div class="border-holder">
            <board :color="'green'" :items="done" :status="'done'" :props="doneProps" ></board>
        </div>
    </div>
</html>