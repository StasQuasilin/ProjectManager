<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 26.02.2020
  Time: 15:30
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <jsp:include page="../subscribePage.jsp"/>
    <script>
        list.api.edit = '${edit}';
        subscribe(list);
    </script>
    <table style="width: 100%; height: 100%; border-collapse: collapse" border="1">
        <tr>
            <td rowspan="2" style="width: 50%; vertical-align: top">
                <div id="list" style="padding: 2pt">
                    <div v-for="t in items" v-on:click="edit(t.id)">
                        <div>
                            {{new Date(t.date).toLocaleDateString()}}
                        </div>
                        <div>
                            {{t.type}}
                            <template v-if="t.type === 'income'">
                                +
                            </template>
                            <span>
                                {{t.sum.toLocaleString()}}
                                <span v-if="t.currency">
                                    {{t.currency.sign}}
                                </span>
                            </span>
                            <span>
                                {{t.category.name}}
                            </span>
                        </div>
                    </div>
                </div>
            </td>
            <td style="vertical-align: top">
                BUDGETS
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top">
                <jsp:include page="buyList.jsp"/>
            </td>
        </tr>
    </table>

</html>
