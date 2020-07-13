<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 06.07.2020
  Time: 14:10
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/tree.vue"></script>
<script>
    tree.api.getChildren = '${getTask}';
  subscriber.subscribe('${subscribe}', tree.handler);
</script>
  <table id="tree" class="full-size">
    <tr>
      <td>
          <template v-for="(i, k) in getPath()">
              <span class="text-button" v-on:click="getChildren(i)">
                  {{k + 1}}. {{i}}
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
        Tree Content
      </td>
    </tr>
  </table>
