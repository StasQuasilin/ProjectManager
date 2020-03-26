<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
<link rel="stylesheet" href="${context}/css/tree/TreeList.css">
<script src="${context}/js/tree/TreeList.js"></script>
    <jsp:include page="../subscribePage.jsp"/>
    <script>
        list.api.edit = '${edit}';
        list.api.getSubs = '${getSubTasks}';
        list.deafaultTaskName = '<fmt:message key="default.task.name"/>'
    </script>
    <div id="tree">
        <div>
            <template v-if="selected">
                <span v-for="p in selected.path">
                    <a>
                        {{p.title}}
                    </a>/
                </span>
                {{selected.title}}
            </template>
            /<span>..</span>
            <div style="display: inline-block" v-if="selected" v-on:click="newTask()">
                +<fmt:message key="tree.add.task"/>
            </div>
        </div>
        <div>
            <div>
                <div v-on:click="select(item)" v-for="item in items" style="background-color: orange; padding: 2pt 4pt; margin: 2pt">
                    <span v-if="item.isGroup">
                        -
                    </span>
                    <span v-else>
                        &nbsp;
                    </span>
                    {{item.title}}
                </div>
            </div>
        </div>
    </div>
</html>
