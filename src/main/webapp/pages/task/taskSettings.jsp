<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 03.04.20
  Time: 10:35
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script src="${context}/vue/templates/findInput.vue"></script>
    <script src="${context}/vue/task/taskSettings.vue"></script>
    <script>
        edit.api.save = '${save}';
        edit.api.getSubTask = '${getSubTasks}';
        edit.task = ${task.toJson()}
        edit.task.path.reverse();
    </script>
    <table id="editor">
        <tr>
            <td>
                <label for="title">
                    <fmt:message key="task.title"/>
                </label>
            </td>
            <td>
                <input id="title" v-model="task.title">
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="task.parent"/>
            </td>
            <td>
                <div style="font-size: 10pt">
                    <span>
                        .. /
                    </span>
                        <span v-for="(p, idx) in task.path">
                        {{p.title}}<span v-on:clicl="removePath(idx)">&times;</span> /
                    </span>
                    <span v-if="parents.length == 0" v-on:click="getPathByParent()">+</span>
                    <div v-else style="position: relative; display: inline-block; height: 10pt">
                        <div style="position: absolute; border: solid 1pt; background-color: white; font-size: 10pt">
                            <div v-on:click="clearParents()">
                                &times;
                            </div>
                            <div v-for="parent in parents" v-on:click="setParent(parent)">
                                {{parent.title}}
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
        <tr>
            <td>
                <fmt:message key="title.task.remove"/>
            </td>
            <td>

            </td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center">
                <button onclick="closeModal()">
                    &times;<fmt:message key="button.close"/>
                </button>
                <button v-on:click="save">
                    <fmt:message key="buttons.save"/>
                </button>
            </td>
        </tr>
    </table>
</html>
