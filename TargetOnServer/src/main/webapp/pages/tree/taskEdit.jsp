
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 14.07.20
  Time: 11:51
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/templates/inputWithSearch.vue?v=${now}"></script>
<script src="${context}/vue/templates/datetime/datePicker.vue?v=${now}"></script>
<script src="${context}/vue/taskEdit.vue?v=${now}"></script>
<script>
    taskEdit.api.save = '${save}';
    taskEdit.props.findCategory = '${findCategory}';
    taskEdit.buyListProps.findCategory = '${findBuyList}';
    taskEdit.dependencyProps.findCategory = '${findCategory}';
    <c:forEach items="${status}" var="s">
    taskEdit.status.push('${s}');
    taskEdit.statusNames['${s}'] = '<fmt:message key="task.${s}"/>';
    </c:forEach>
    <c:if test="${not empty parent}">
    taskEdit.task.parent = ${parent.toJson()}
    </c:if>
    <c:choose>
    <c:when test="${not empty task}">
    taskEdit.task = ${task.toJson()};
    if (taskEdit.task.deadline){
        taskEdit.installDeadline = true;
    }
    </c:when>
    <c:otherwise>
    taskEdit.task.title = '<fmt:message key="task.title.default"/>';
    taskEdit.task.status = 'active';
    </c:otherwise>
    </c:choose>
    <c:choose>
    <c:when test="${not empty buyList}">
    taskEdit.addToBuyList = true;
    taskEdit.task.buyList = ${buyList.toJson()}
    </c:when>
    <c:when test="${not empty rootBuyList}">
    taskEdit.task.buyList = ${rootBuyList.toJson()};
    </c:when>
    </c:choose>
    if (!taskEdit.task.date){
        taskEdit.task.date = new Date().toISOString().substring(0, 10);
    }
    if (!taskEdit.task.deadline){
        taskEdit.task.deadline = new Date().toISOString().substring(0, 10);
    }
    <c:forEach items="${dependent}" var="d">
    taskEdit.dependent.push(${d.toJson()});
    </c:forEach>
</script>
<div id="taskEdit" style="display: flex">
    <table>
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
        <tr v-if="status.includes(task.status)">
            <td>
                <label for="status">
                    <fmt:message key="task.current.status"/>
                </label>
            </td>
            <td>
                <select id="status" v-model="task.status">
                    <option v-for="s in status" :value="s">
                        {{statusNames[s]}}
                    </option>
                </select>
            </td>
        </tr>
        <tr>
            <td>

            </td>
        </tr>
        <tr v-if="task.parent">
            <td>
                <fmt:message key="task.parent"/>
            </td>
            <td>
                <find-input :object="task.parent" :props="props"></find-input>
            </td>
        </tr>
        <tr v-if="!installDeadline">
            <td colspan="2" style="text-align: right">
            <span class="text-button" v-on:click="installDeadline = true">
                <fmt:message key="task.install.deadline"/>
            </span>
            </td>
        </tr>
        <tr v-else>
            <td>
                <fmt:message key="task.deadline"/>
            </td>
            <td>
                <date-picker :date="task.deadline" :props="deadlineProps"></date-picker>
                <span class="text-button" v-on:click="installDeadline = false">
                &times;
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
                    <date-picker :date="task.date" :props="dateProps"></date-picker>
                    <span class="text-button" v-on:click="useDate=false">
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
                        <input type="radio" name="notDoneAction">
                        <fmt:message key="task.next.day"/>
                    </div>
                    <div>
                        <input type="radio" name="notDoneAction">
                        <fmt:message key="task.status.paused.set"/>
                    </div>
                </td>
            </tr>
        </template>
        <tr v-if="!addToBuyList">
            <td colspan="2">
            <span class="text-button" v-on:click="addToBuyList=true">
                <fmt:message key="task.to.buy.list"/>
            </span>
            </td>
        </tr>
        <template v-else>
            <tr>
                <td>
                    <fmt:message key="buy.list"/>
                </td>
                <td>
                    <find-input :object="task.buyList" :props="buyListProps"></find-input>
                </td>
            </tr>
        </template>
        <tr>
            <td colspan="2">
                <input id="doneIf" type="checkbox" v-model="task.doneIf">
                <fmt:message key="task.done.if.children"/>
            </td>
        </tr>
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
    <div>
        <fmt:message key="dependency"/>
        <div v-for="d in dependent">
            {{d.title}}
        </div>
        <div>
            <span v-if="!addDependency" v-on:click="addDependency = true">
                <fmt:message key="dependency.add"/>
            </span>
            <div v-else>
                <find-input :object="dependency" :props="dependencyProps"></find-input>
            </div>
        </div>
    </div>
</div>
