
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
<script src="${context}/vue/templates/inputWithSearch.vue"></script>
<script src="${context}/vue/taskEdit.vue"></script>
<script>
    taskEdit.api.save = '${save}';
    taskEdit.props.findCategory = '${findCategory}';
    taskEdit.buyListProps.findCategory = '${findBuyList}';
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
    <c:if test="${not empty buyList}">
    taskEdit.addToBuyList = true;
    taskEdit.task.buyList = ${buyList.toJson()}
    </c:if>
    </c:when>
    <c:otherwise>
    taskEdit.task.title = '<fmt:message key="task.title.default"/>';
    taskEdit.task.status = 'active';
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
    <tr>
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
    <tr v-if="!addToBuyList">
        <td colspan="2">
            <span class="text-button" v-on:click="addToBuyList=true">
                <fmt:message key="task.to.buy.list"/>
            </span>

        </td>
    </tr>
    <tr v-else>
        <td>
            <fmt:message key="buy.list"/>
        </td>
        <td>
            <find-input :object="task.buyList" :props="buyListProps"></find-input>
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