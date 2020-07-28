<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:10
--%>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/tree.css">
<script src="${context}/vue/pathBuilder.vue"></script>
<script src="${context}/vue/tree.vue"></script>
<script>
    tree.api.edit = '${edit}';
    tree.api.getChildren = '${getTask}';
    tree.api.treeBuilder = '${treeBuilder}';
    <c:forEach items="${goals}" var="goal">
    tree.goals.push(${goal.toJson()});
    </c:forEach>
    tree.getChildren('${item}');

    subscriber.subscribe('${subscribe}', tree.handler);
</script>
  <table id="tree" class="full-size" border="1">
    <tr>
      <td>
          <template v-for="(i, k) in path" class="tree-path">
              <span class="text-button" v-on:click="getChildren(i.category)">
                  {{i}}
              </span>
              <span v-if="k < path.length - 1">
                  /
              </span>
          </template>
      </td>
        <td style="min-width: 20%">
            <select v-model="root" v-if="root" v-on:change="changeRoot()" style="width: 100%">
                <option v-for="goal in goals" :value="goal.id">
                    {{goal.title}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
      <td style="width: 60%; height: 100%">
          <div class="task-container item-container">
              <div class="task-container-title task-container-title-active">
                  <span>
                  <fmt:message key="task.active"/>
                    </span>
                  <button v-on:click="newTask()">
                      <fmt:message key="task.add"/>
                  </button>
              </div>
              <div v-for="item in childrenByStatus('active')" v-on:click="edit(item.id)">
                  {{item.id}}. {{item.title}}
              </div>
              <div class="task-container-title task-container-title-progressing">
                  <span>
                  <fmt:message key="task.progressing"/>
                </span>
              </div>
              <div v-for="item in childrenByStatus('progressing')" v-on:click="edit(item.id)">
                  {{item.id}}. {{item.title}}
              </div>
              <div class="task-container-title task-container-title-done">
                  <span>
                  <fmt:message key="task.done"/>
                </span>
              </div>
              <div v-for="item in childrenByStatus('done')" v-on:click="edit(item.id)">
                  {{item.id}}. {{item.title}}
              </div>
          </div>
      </td>
        <td style="width: 20%">
            <div class="item-container">
                <tree-view :item="tree" :props="props"></tree-view>
            </div>
        </td>
    </tr>
  </table>
