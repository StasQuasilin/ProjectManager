<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 26.02.2020
  Time: 15:30
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <div id="title-content" style="display: inline-block; background: transparent; margin: 0 4pt">
        <button onclick="loadModal('${edit}')">
            <fmt:message key="transaction.add"/>
        </button>
        <button onclick="loadModal('${plan}')">
            <fmt:message key="transaction.plan"/>
        </button>
    </div>
    <script>
        var projectBox = {}
    </script>
    <script src="${context}/vue/projects/projectList.vue"></script>
    <script>
        list.api.edit = '${edit}'
    </script>
    <jsp:include page="../subscribePage.jsp"/>
    <div id="list" style="padding: 2pt">
        <div v-for="t in items" v-on:click="edit(t.id)">
            <div>
                {{new Date(t.date).toLocaleDateString()}}
            </div>
            <div>
                <template v-if="t.type === 'income'">
                    +
                </template>
                <span>
                    {{t.sum.toLocaleString()}} {{t.currency.sign}}
                </span>
                <span>
                    {{t.category.name}}
                </span>
            </div>
        </div>
    </div>
</html>
