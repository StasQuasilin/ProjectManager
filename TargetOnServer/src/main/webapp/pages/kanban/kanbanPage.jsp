
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 26.08.20
  Time: 08:54
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/kanban.css"/>
<script type="application/javascript" src="${context}/vue/kanban/kanbanPage.vue"></script>
<script type="application/javascript">
    <c:forEach items="${statuses}" var="status">
    kanban.statuses.push('${status}');
    kanban.statusNames['${status}'] = '<fmt:message key="task.${status}"/>';
    </c:forEach>
    <c:forEach items="${goals}" var="goal">
    kanban.goals.push(${goal.shortJson()});
    </c:forEach>
</script>
<div id="kanban">
    <div id="titleContent" class="title-content">
        <label for="goals">
            <fmt:message key="title.goals"/>
        </label>
        <select id="goals" v-if="goals" v-model="goal">
            <option v-for="g in goals" :value="g.id">
                {{g.title}}
            </option>
        </select>
        <span class="text-button">
            &plus;
        </span>
    </div>
    <div class="full-size">
        <div class="kanban-board-wrapper">
            <div class="kanban-board" v-for="status in statuses">
                <div class="kanban-board-header-wrapper">
                    <div class="kanban-board-header">
                        {{statusNames[status]}}
                    </div>
                </div>
                <div class="item-container-wrapper">
                    <div class="item-container">
                        <div v-for="item in getItemsByStatus('status')">
                            {{item}}
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
