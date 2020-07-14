<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:10
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<link rel="stylesheet" href="${context}/css/tree.css">
<script src="${context}/vue/tree.vue"></script>
<script>
    tree.api.edit = '${edit}';
    tree.api.getChildren = '${getTask}';
    tree.getChildren('${item}');
    subscriber.subscribe('${subscribe}', tree.handler);
</script>
  <table id="tree" class="full-size">
    <tr>
      <td>
          <template v-for="(i, k) in getPath()" class="tree-path">
              <span class="text-button" v-on:click="getChildren(i)">
                  {{i}}
              </span>
              <span v-if="k < getPath().length - 1">
                  /
              </span>
          </template>
      </td>
      <td rowspan="2" style="width: 20%">
        - Tree
          - View
      </td>
    </tr>
    <tr>
      <td class="full-size">
          <div class="task-container">
              <span class="task-container-title">
                  <fmt:message key="tast.active"/>
              </span>
              <button v-on:click="newTask()">
                  <fmt:message key="task.add"/>
              </button>
              <div v-for="item in childrenByStatus('active')" v-on:click="edit(item.id)">
                  {{item.id}}. {{item.title}}
              </div>
          </div>
      </td>
    </tr>
  </table>
