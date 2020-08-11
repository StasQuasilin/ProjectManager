<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:10
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<link rel="stylesheet" href="${context}/css/tree.css">
<script type="application/javascript" src="${context}/vue/pathBuilder.vue"></script>
<script type="application/javascript" src="${context}/vue/tree.vue"></script>
<script type="application/javascript">
    tree.api.edit = '${edit}';
    tree.api.delete = '${delete}';
    tree.api.getChildren = '${getTask}';
    tree.api.treeBuilder = '${treeBuilder}';
    tree.api.timer = '${taskTimer}';
    <c:forEach items="${status}" var="s">
    tree.status.push('${s}');
    tree.statusNames['${s}'] = '<fmt:message key="task.${s}"/>';
    </c:forEach>
    <c:forEach items="${goals}" var="goal">
    tree.goals.push(${goal.toJson()});
    </c:forEach>
    tree.getChildren('${item}');

    subscriber.subscribe('${subscribe}', tree.handler);
</script>
<div id="tree" class="full-size">
    <template v-if="root !== -1">
        <div class="central-panel">
            <div class="panel-header">
                <div class="panel-header-content task-path">
                    <template v-for="(i, k) in path" class="tree-path">
                      <span class="text-button" v-on:click="getChildren(i.id)">
                          {{i.title}}
                      </span>
                        >
                    </template>
                    <b>
                        {{item.title}}
                    </b>
                </div>
            </div>
            <div class="panel-content">
                <div class="task-container item-container">
                    <button v-on:click="newTask()">
                        <fmt:message key="task.add"/>
                    </button>
                    <template v-for="s in status">
                        <template v-if="childrenByStatus(s).length > 0">
                            <div class="task-container-title" :class="'task-container-title-' + s">
                                <span>
                                    {{statusNames[s]}}
                                </span>
                            </div>
                            <div v-for="item in childrenByStatus(s)" class="task-item">
                                <span>
                                    {{item.id}}. {{item.title}}
                                </span>
                                <span class="task-menu" v-if="item.status !== 'done'">
                                    <span class="text-button" v-on:click="timer(item.id)">
                                        🕐🕐🕐🕐&#128336;
                                    </span>
                                    <span class="text-button edit-button" v-on:click="edit(item.id)"></span>
                                    <span class="text-button delete-button" v-on:click="deleteTask(item.id)"></span>
                                </span>
                            </div>
                        </template>
                    </template>
                </div>
            </div>
        </div>
        <div class="right-panel">
            <div class="panel-header">
                <div class="panel-header-content">
                    <select v-model="root" v-if="root" v-on:change="changeRoot()" style="width: 100%">
                        <option v-for="goal in goals" :value="goal.category">
                            {{goal.title}}
                        </option>
                    </select>
                </div>
            </div>
            <div class="panel-content">
                <div class="item-container">
                    <tree-view :item="tree" :key="tree.id" :props="props" :current="item.id"></tree-view>
                </div>
            </div>
        </div>
    </template>
    <div v-else class="item-container task-container" style="width: 100%">
        <div v-for="g in goals" class="tree-tile" v-on:click="setRoot(g)">
            {{g.title}}
        </div>
    </div>
</div>

