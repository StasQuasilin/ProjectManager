<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 06.08.20
  Time: 13:36
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<script type="application/javascript" src="${context}/vue/templates/inputWithSearch.vue"></script>
<script type="application/javascript" src="${context}/vue/tree/taskTimer.vue"></script>
<script type="application/javascript">
    timer.api.timerStart = '${timerStart}';
    timer.api.timerStop = '${timerStop}';
    timer.taskProps.findCategory = '${findTask}';
    <c:choose>
    <c:when test="${not empty timeLog}">
    timer.timer = ${timeLog.toJson()};
    timer.updateLength();
    </c:when>
    <c:otherwise>
    <c:if test="${not empty task}">
    timer.timer.task =${task.shortJson()}
    </c:if>
    </c:otherwise>
    </c:choose>

</script>
<table id="timer">
    <tr>
        <td>
            <fmt:message key="task"/>
        </td>
        <td>
            <find-input :object="timer.task" :props="taskProps" :enable="timer.begin != null"></find-input>
        </td>
    </tr>
    <template v-if="timer.begin">
        <tr>
            <td>
                <fmt:message key="timer.start"/>
            </td>
            <td>
                {{new Date(timer.begin).toLocaleDateString()}}
                {{new Date(timer.begin).toLocaleTimeString().substring(0, 5)}}
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="timer.length"/>
            </td>
            <td>
                <span v-if="timerLength.hours > 0">
                    {{prettyNumber(timerLength.hours)}}
                    <fmt:message key="time.hour"/>
                </span>
                <span v-if="timerLength.min > 0">
                    {{prettyNumber(timerLength.min)}}
                    <fmt:message key="time.min"/>
                </span>
                <span>
                    {{prettyNumber(timerLength.sec)}}
                    <fmt:message key="time.sec"/>
                </span>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <span class="text-button" style="color: orangered" v-on:click="timerStop()">
                    &#9632;
                </span>
            </td>
        </tr>
    </template>
    <tr v-else>
        <td colspan="2" style="text-align: center">
            <span class="text-button" style="color: green" v-on:click="timerStart()">
                &#9654;
            </span>
        </td>
    </tr>
</table>
