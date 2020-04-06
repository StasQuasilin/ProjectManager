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
        kanban.api.show = '${show}';
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

        var props = {
            open:function(task){
                kanban.open(task)
            },
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
        subscribe(kanban);

    </script>
    <div id="kanban" style="height: 100%; width: 100%">
        <table style="width: 100%; height: 100%; border-collapse: collapse">
            <tr>
                <td class="board-cell">
                    <div class="border-holder">
                        <board :color="'green'" :items="todo" :status="'active'" :props="todoProps"></board>
                    </div>
                </td>
                <td class="board-cell">
                    <div class="border-holder">
                        <board :color="'green'" :items="inProgress" :status="'progressing'" :props="processingProps"></board>
                    </div>
                </td>
                <td class="board-cell">
                    <div class="border-holder">
                        <board :color="'green'" :items="done" :status="'done'" :props="doneProps" ></board>
                    </div>
                </td>
            </tr>
        </table>
    </div>
</html>