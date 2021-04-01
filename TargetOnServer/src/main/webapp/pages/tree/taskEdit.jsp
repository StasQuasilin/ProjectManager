
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
    taskEdit.api.discussionEdit = '${discussionEdit}';
    taskEdit.props.findCategory = '${findCategory}';
    taskEdit.buyListProps.findCategory = '${findBuyList}';
    taskEdit.dependencyProps.findCategory = '${findDependency}';
    <c:forEach items="${status}" var="s">
    taskEdit.status.push('${s}');
    taskEdit.statusNames['${s}'] = '<fmt:message key="${s}"/>';
    </c:forEach>
    <c:forEach items="${types}" var="t">
    taskEdit.types.push('${t}');
    taskEdit.typeNames['${t}'] = '<fmt:message key="${t}"/>';
    taskEdit.typeDescriptions['${t}'] = '<fmt:message key="${t}.description"/>';
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
    taskEdit.task.type = taskEdit.types[1];
    </c:otherwise>
    </c:choose>
    <c:choose>
    <c:when test="${not empty buyList}">
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
    if(typeof taskEdit.task.dependency === "undefined"){
        taskEdit.task.dependency = [];
    }
    if (typeof taskEdit.task.discussions === "undefined"){
        taskEdit.task.discussions = [];
    }
    <c:forEach items="${discussions}" var="d">
    taskEdit.task.discussions.push(${d.toJson()});
    </c:forEach>
    <c:forEach items="${dependent}" var="d">
    taskEdit.task.dependency.push(${d.toJson()});
    </c:forEach>
    <c:if test="${not empty buyList}">
    taskEdit.buyList = ${buyList.shortJson()}
    </c:if>
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
               <label for="type">
                   <fmt:message key="task.type"/>
               </label>
            </td>
            <td>
                <select id="type" v-model="task.type">
                    <option v-for="t in types" :value="t">
                        {{typeNames[t]}}
                    </option>
                </select>
                <span class="tips">
                    <span class="tips-content">
                        {{typeDescriptions[task.type]}}
                    </span>
                </span>
            </td>
        </tr>
        <template v-if="task.type === 'accumulative'">
            <tr>
                <td>
                    <label for="taskTarget">
                        <fmt:message key="task.accumulative.target"/>
                    </label>
                </td>
                <td>
                    <input id="taskTarget" v-model="task.target" autocomplete="off" onfocus="this.select()">
                </td>
            </tr>
            <tr>
                <td>
                    <label for="taskProgress">
                        <fmt:message key="task.accumulative.progress"/>
                    </label>
                </td>
                <td>
                    <input id="taskProgress" v-model="task.progress" autocomplete="off" onfocus="this.select()">
                </td>
            </tr>
        </template>
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
                <span class="text-button" v-on:click="enableBuyList()">
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
            <tr>
                <td>
                    <label for="coast">
                        <fmt:message key="task.coast"/>
                    </label>
                </td>
                <td>
                    <input id="coast" v-model="task.coast" onfocus="this.select()" autocomplete="off">
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
                <span class="text-button" v-on:click="addDependency = true"
                      v-if="task.dependency.length === 0 && !addDependency">
                    <fmt:message key="dependency.add"/>
                </span>
                <span class="text-button"  v-on:click="createDiscussion()"
                    v-if="task.discussions.length === 0">
                    <fmt:message key="task.discussion.add"/>
                </span>
            </td>
        </tr>
    </table>
    <c:if test="${task.id gt 0}">
        <div v-if="(task.dependency && task.dependency.length > 0) || addDependency">
            <div style="width: 100%; text-align: center">
                <fmt:message key="dependency"/>
                <span class="tips">
                <span class="tips-content">
                    <fmt:message key="task.dependency.tips"/>
                </span>
            </span>
            </div>
            <div v-for="(d, dIdx) in task.dependency" style="font-size: 10pt">
                <span class="text-button" v-on:click="removeDependency(dIdx)">
                    &times;
                </span>
                <span>
                    {{d.title}}
                </span>
            </div>
            <div>
            <span v-if="!addDependency" v-on:click="addDependency = true">
                <fmt:message key="dependency.add"/>
            </span>
                <div v-else>
                    <find-input :object="dependency" :props="dependencyProps" :show="'title'" :additionally="additionally"></find-input>
                    <span class="text-button" v-on:click="addDependency = false">
                        &times;
                    </span>
                </div>
            </div>
        </div>
        <div v-if="task.discussions.length > 0">
            <fmt:message key="task.discussion"/>
            <span class="text-button" style="font-size: 10pt" v-on:click="createDiscussion()">
                +<fmt:message key="button.add"/>
            </span>
            <div v-for="d in task.discussions">
                <span v-html="d.text"></span>
                <br>
                <span style="font-size: 8pt">
                    {{new Date(d.time).toLocaleString()}}
                    {{d.author.surname}} {{d.author.forename}}
                </span>
            </div>
        </div>
    </c:if>
    <div class="modal-buttons">
        <button onclick="closeModal()">
            <fmt:message key="button.cancel"/>
        </button>
        <button v-on:click="save">
            <fmt:message key="button.save"/>
        </button>
    </div>
</div>
