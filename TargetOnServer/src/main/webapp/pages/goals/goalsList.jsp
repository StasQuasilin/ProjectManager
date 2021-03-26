<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 18.06.2020
  Time: 18:25
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <link rel="stylesheet" href="${context}/css/goals.css?v=${now}"/>
    <link rel="stylesheet" href="${context}/css/progressBar.css?v=${now}">
    <script type="application/javascript" src="${context}/vue/templates/progressBar.vue?v=${now}"></script>
    <script type="application/javascript" src="${context}/vue/goals/goalTile.vue?v=${now}"></script>
    <script type="application/javascript" src="${context}/vue/goalList.vue?v=${now}"></script>
    <script>
        goalList.api.edit = '${goalEdit}';
        goalList.api.tree = '${tree}';
        goalList.api.remove = '${remove}';
        goalList.props.budget = '<fmt:message key="goal.budget"/>';
        goalList.props.dateBegin = '<fmt:message key="goal.begin.date"/>';
        goalList.props.dateEnd = '<fmt:message key="goal.end.date"/>';
        goalList.props.activeTasks = '<fmt:message key="goal.active.tasks"/>';
        goalList.props.progressingTasks = '<fmt:message key="goal.progressing.task"/>';
        goalList.props.doneTasks = '<fmt:message key="goal.done.tasks"/>';

    </script>
    <jsp:include page="goalHeader.jsp"/>
    <div id="goalList" class="goal-page item-container">
<%--        <template v-if="itemsCount > 0">--%>
            <goal-tile v-for="goal in getItems()"
                   :tree="openTree" :key="goal.id" :goal="goal" :props="props"
                       class="goal-item"></goal-tile>
<%--        </template>--%>
<%--        <p v-else>--%>
<%--            ...Press add for do something...--%>
<%--        </p>--%>
    </div>
</html>
