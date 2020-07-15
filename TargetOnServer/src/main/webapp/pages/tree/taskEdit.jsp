
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 14.07.20
  Time: 11:51
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/taskEdit.vue"></script>
<script>
    taskEdit.api.save = '${save}';
    <c:if test="${not empty parent}">
    taskEdit.task.parent = ${parent.toJson()}
    </c:if>
    <c:choose>
    <c:when test="${not empty task}">
    taskEdit.task = ${task.toJson()}
    </c:when>
    <c:otherwise>
    taskEdit.task.title = '<fmt:message key="task.title.default"/>';
    </c:otherwise>
    </c:choose>
</script>
<table id="taskEdit">
    <tr>
        <td colspan="2">
            <label for="title">
                <fmt:message key="task.title"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="title" v-model="task.title" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr v-if="task.parent">
        <td>
            <fmt:message key="task.parent"/>
        </td>
        <td>
            <span class="text-button">
                {{task.parent.title}}
            </span>
        </td>
    </tr>
    <tr v-if="!useDate">
        <td colspan="2">
            <span class="text-button" v-on:click="useDate = true">
                <fmt:message key="task.date.add"/>
            </span>
        </td>
    </tr>
    <template v-else>
        <tr>
            <td>
                <fmt:message key="task.date"/>
            </td>
            <td>
            <span class="text-button">
                {{new Date(task.date).toLocaleDateString()}}
            </span>
                <span class="text-button" v-on:click="useDate = false">
                &times;
            </span>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="task.not.done.action"/>
            </td>
            <td>
                <div>
                    <input type="radio">
                    <fmt:message key="task.next.day"/>
                </div>
                <div>
                    <input type="radio">
                    <fmt:message key="task.status.paused.set"/>
                </div>
            </td>
        </tr>
    </template>
    <tr>
        <td colspan="2" style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>