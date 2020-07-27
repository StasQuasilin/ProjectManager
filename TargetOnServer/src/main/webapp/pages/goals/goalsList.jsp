<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 18.06.2020
  Time: 18:25
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <link rel="stylesheet" href="${context}/css/goals.css"/>
    <script src="${context}/vue/goals/goalTile.vue"></script>
    <script src="${context}/vue/goalList.vue"></script>
    <script>
        goalList.api.edit = '${edit}';
        goalList.api.tree = '${tree}';
        subscriber.subscribe('${subscribe}', goalList.handler)
    </script>
    <jsp:include page="goalHeader.jsp"/>
    <body>
        <div id="goalList" class="goal-page item-container">
            <goal-tile v-for="goal in getItems()" :key="goal.id" :goal="goal" :props="props"
                       class="goal-item"></goal-tile>
        </div>
    </body>
</html>
