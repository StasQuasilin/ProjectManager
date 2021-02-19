<%--
  Created by IntelliJ IDEA.
  User: kvasilin
  Date: 19.02.2021
  Time: 20:35
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
    <div style="display: flex; width: 100%; height: 100%">
        <div>
            <jsp:include page="../goals/goalsList.jsp"/>
        </div>
        <div>
            <jsp:include page="treeView.jsp"/>
        </div>
    </div>
<script>
    goalList.treeView = treeView;
</script>
</html>
