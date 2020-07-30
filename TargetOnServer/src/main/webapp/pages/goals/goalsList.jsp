<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 18.06.2020
  Time: 18:25
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <link rel="stylesheet" href="${context}/css/goals.css"/>
    <link rel="stylesheet" href="${context}/css/progressBar.css">
    <script src="${context}/vue/templates/progressBar.vue"></script>
    <script src="${context}/vue/goals/goalTile.vue"></script>
    <script src="${context}/vue/goalList.vue"></script>
    <script>
        goalList.api.edit = '${edit}';
        goalList.api.tree = '${tree}';
        goalList.api.remove = '${remove}';
        goalList.props.budget = '<fmt:message key="goal.budget"/>';
        goalList.props.dateBegin = '<fmt:message key="goal.begin.date"/>';
        goalList.props.dateEnd = '<fmt:message key="goal.end.date"/>';
        goalList.props.activeTasks = '<fmt:message key="goal.active.tasks"/>';
        goalList.props.progressingTasks = '<fmt:message key="goal.progressing.task"/>';
        goalList.props.doneTasks = '<fmt:message key="goal.done.tasks"/>';
        subscriber.subscribe('${subscribe}', goalList.handler)
    </script>
    <jsp:include page="goalHeader.jsp"/>
    <body>
        <div id="goalList" class="goal-page item-container">
            <goal-tile v-for="goal in getItems()" :key="goal.id" :goal="goal" :props="props"
                       class="goal-item"></goal-tile>
        </div>
    </body>
</html>
