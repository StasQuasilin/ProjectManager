<%--
  Created by IntelliJ IDEA.
  User: Kvasik
  Date: 18.06.2020
  Time: 18:25
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <script src="${context}/vue/goalList.vue"></script>
    <script>
        subscriber.subscribe('${subscribe}', goalList.handler)
    </script>
    <body>
        <div id="goalList">
            {{test}}
        </div>
        GOALS
    </body>
</html>
