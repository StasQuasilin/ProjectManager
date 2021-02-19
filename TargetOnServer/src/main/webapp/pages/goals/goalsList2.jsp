<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%--<script src="${context}/vue/templates/progressBar.vue"></script>--%>
<%--<script src="${context}/vue/goals/goalTile.vue"></script>--%>
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
    subscriber.subscribe('${goalSubscribe}', goalList.handler);
</script>
<jsp:include page="goalHeader.jsp"/>
<div id="goalList">
    <div v-for="goal in items">
        {{goal}}
    </div>
</div>