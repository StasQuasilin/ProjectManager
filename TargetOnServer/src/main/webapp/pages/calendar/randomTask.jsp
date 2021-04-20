
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-04-20
  Time: 09:15
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/calendar/randomTask.vue"></script>

<html>
<script>
    randomTask.api.getTask = '${randomTask}';
    randomTask.api.save = '${save}';
    <c:forEach items="${goals}" var="goal">
    randomTask.goals.push(${goal.shortJson()});
    </c:forEach>
    randomTask.data.goal = randomTask.goals[0].id;
</script>
<table id="editor">
    <tr>
        <td>
            <label for="goal">
                <fmt:message key="title.goal"/>
            </label>
        </td>
        <td>
            <select id="goal" v-model="data.goal">
                <option v-for="d in goals" :value="d.id">
                    {{d.title}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="count">
                <fmt:message key="task.count"/>
            </label>
        </td>
        <td>
            <input id="count" type="number" v-model="data.count" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <div style="border: solid gray 1pt; max-height: 100pt; overflow-y: scroll">
                <div v-for="(r, rId) in result">
                    <span v-on:click="removeItem(rId)">
                        &times;
                    </span>
                    <span style="font-size: 10pt">
                        <template v-if="pIdx > 0" v-for="(p, pIdx) in r.parent.path">
                            {{p.title}} /
                        </template>
                        {{r.parent.title}} /
                    </span>
                    <span>
                        {{r.title}}
                    </span>
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td colspan="2" style="text-align: center">
            <button v-on:click="generate()">
                <fmt:message key="generate"/>
            </button>
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


</html>
