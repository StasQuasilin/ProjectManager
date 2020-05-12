<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 13.04.20
  Time: 16:20
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <script src="${context}/vue/filters/kanbanFilter.vue"></script>
    <div id="filter">
        <select>
            <option v-for="p in projects">
                {{p.title}}
            </option>
        </select>

    </div>
</html>
