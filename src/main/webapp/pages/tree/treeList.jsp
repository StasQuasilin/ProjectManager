<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="ua"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${context}/css/tree/TreeList.css">
<script src="${context}/js/tree/TreeList.js"></script>
<script>
    tree.api.update='${update}';
    tree.api.edit = '${edit}';
    tree.update();
</script>
<div id="tree">
    <div style="text-align: left" class="tree-title">
        <a v-on:click="select(-1)"><fmt:message key="tree.all"/></a>/<a>{{tree.title}}</a>
        <span v-if="tree.title" class="add-button" :parent="selected" v-on:click="newTask">
            +<fmt:message key="tree.add.task"/>
        </span>
    </div>
    <div v-for="t in tree.tasks" v-on:click="select(t.id)">
        <div :id="t.id" v-on:click="select(t.id)" class="child-box">
            <span v-if="t.status == 'active'">

            </span>
            <span>
                {{t.title}}
            </span>
        </div>

    </div>
</div>

</html>
