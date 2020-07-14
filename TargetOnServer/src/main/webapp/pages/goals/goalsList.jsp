<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 18.06.2020
  Time: 18:25
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <link rel="stylesheet" href="${context}/css/goals.css"/>
    <script src="${context}/vue/goalList.vue"></script>
    <script>
        goalList.api.tree = '${tree}';
        subscriber.subscribe('${subscribe}', goalList.handler)
    </script>
    <jsp:include page="goalHeader.jsp"/>
    <body>
        <div id="goalList" class="goal-page">
            <div v-for="goal in getItems()" class="goal-item">
                <div v-on:click="openTree(goal.id)" class="text-button">
                    TREEEEEE
                </div>
                <div>
                    {{goal}}
                </div>

            </div>
        </div>
    </body>
</html>
