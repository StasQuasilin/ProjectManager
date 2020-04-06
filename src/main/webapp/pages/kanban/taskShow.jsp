<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 06.04.20
  Time: 16:04
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/kanban/taskShow.vue"></script>
    <script>
        show.api.startTimer = '${timerStart}';
        show.api.stopTimer = '${timerStop}';
        show.task = ${task.toJson()};
        <c:forEach items="${timers}" var="timer">
        show.addTimer(${timer.toJson()});
        </c:forEach>
    </script>
    <table id="show">
        <tr>
            <td colspan="2">
                {{task.title}}
            </td>
        </tr>
        <tr>
            <td>

            </td>
            <td>
                <div>
                    <fmt:message key="task.time.log"/>
                    <button v-if="!haveActiveTimer" v-on:click="startTimer()">
                        <fmt:message key="button.timer.start"/>
                    </button>
                </div>
                <div v-for="time in timeLog">
                    <span v-if="!time.end">
                        <img style="width: 10px" src="${context}/img/buttons/active.svg" alt="">
                    </span>
                    <span>
                        {{buildTimeRow(time.begin)}}
                    </span>
                    <span v-if="time.end">
                        - {{buildTimeRow(time.end)}}
                        {{calculateSpend(time.begin, time.end)}}
                    </span>
                    <button v-if="!time.end" v-on:click="stopTimer(time)">
                        <fmt:message key="button.timer.stop"/>
                    </button>
                </div>
            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    <fmt:message key="button.close"/>
                </button>
            </td>
        </tr>
    </table>
</html>
