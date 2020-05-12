<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 18.02.2020
  Time: 13:50
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <body>
        <script src="${context}/vue/calendar/taskEdit.vue"></script>
        <script>
            edit.api.save = '${save}';
            <c:forEach items="${parents}" var="parent">
            edit.parents.push(${parent.toJson()});
            </c:forEach>
            edit.task.parent.id = edit.parents[0].id;
            <c:if test="${not empty date}">
                edit.task.date = '${date} ${time}:00:00';
                edit.task.end = '${date} ${time + 1}:00:00';
            </c:if>

            <c:if test="${not empty task}">
            edit.task = ${task.toJson()}
            if (!edit.task.date){
                edit.task.date = -1;
            }
            </c:if>

        </script>
        <table id="editor">
            <tr>
                <td colspan="2">
                    <label for="title">
                        <fmt:message key="task.title"/>*
                    </label>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <input id="title" v-model="task.title" style="width: 100%" autocomplete="off" onfocus="this.select()">
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="task.from"/>
                </td>
                <td>
                    <span>
                        <span v-if="task.date == -1">
                            <fmt:message key="none"/>
                        </span>
                        <span v-else>
                            {{new Date(task.date).toLocaleDateString()}}
                            {{new Date(task.date).toLocaleTimeString().substring(0, 5)}}
                        </span>
                    </span>
                </td>
            </tr>
            <tr>
                <td>
                    <fmt:message key="task.to"/>
                </td>
                <td>
                    <span>
                        <span v-if="task.end == -1">
                            <fmt:message key="none"/>
                        </span>
                        <span v-else>
                            {{new Date(task.end).toLocaleDateString()}}
                            {{new Date(task.end).toLocaleTimeString().substring(0, 5)}}
                        </span>
                    </span>
                </td>
            </tr>
            <tr>
                <td>
                    <label for="length">
                        <fmt:message key="task.length"/>
                    </label>
                </td>
                <td>
                    <span v-on:click="plusLength(-1)">
                        -
                    </span>
                    <input id="length" style="width: 5em; text-align: center" autocomplete="off" v-model.number="task.length" v-on:keyup="checkLength()">
                    <span v-on:click="plusLength(1)">
                        +
                    </span>
                </td>
            </tr>
            <tr >
                <td>
                    <label for="parent">
                        <fmt:message key="task.group"/>
                    </label>
                </td>
                <td>
                    <select id="parent" v-model="task.parent.id">
                        <option v-for="parent in parents" :value="parent.id">
                            {{parent.title}}
                        </option>
                        <option value="-2">
                            <fmt:message key="task.group.add"/>
                        </option>
                    </select>
                </td>
            </tr>
            <tr v-if="task.parent.id == -2">
                <td>
                    <label for="groupName">
                        <fmt:message key="task.group.name"/>
                    </label>
                </td>
                <td>
                    <input id="groupName" v-model="task.parent.title" autocomplete="off">
                </td>
            </tr>
            <tr v-if="!editDescription">
                <td colspan="2">
                    <span v-on:click="editDescription = !editDescription">
                        <fmt:message key="description.add"/>
                    </span>
                </td>
            </tr>
            <template v-else>
                <tr>
                    <td colspan="2">
                        <label for="description">
                            <fmt:message key="task.description"/>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <textarea id="description" style="width: 100%">{{task.description}}</textarea>
                    </td>
                </tr>
            </template>
            <tr>
               <td colspan="2" align="center">
                   <button onclick="closeModal()">
                       <fmt:message key="buttons.cancel"/>
                   </button>
                   <button v-on:click="save()">
                       <fmt:message key="buttons.save"/>
                   </button>
               </td>
            </tr>
        </table>
    </body>
</html>
