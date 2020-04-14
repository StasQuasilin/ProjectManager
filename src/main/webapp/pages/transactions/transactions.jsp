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
    <script src="${context}/vue/templates/transactionItem.vue"></script>
    <script src="${context}/vue/templates/list.vue"></script>
    <script src="${context}/vue/transactions/fastTransactions.vue"></script>
    <script src="${context}/vue/transactions/transactionView.vue"></script>
    <script src="${context}/vue/transactions/accountsView.vue"></script>
    <script>
        transactionList.api.edit = '${edit}';
        transactionList.sort = function(){
            transactionList.items.sort(function (a, b) {
                return new Date(b.date) - new Date(a.date);
            })
        }
        accounts.sort = function(){
            accounts.items.sort(function (a, b) {
                return a.title.localeCompare(b.title);
            })
        }
        accounts.api.edit = '${accountEdit}'
        subscriber.subscribe('${transactions}', transactionList.handle);
        subscriber.unSubscribers.push('${transactions}');
        subscriber.subscribe('${fastTransactions}', fastTransactions.handle);
        subscriber.unSubscribers.push('${fastTransactions}');
        subscriber.subscribe('${accounts}', accounts.handle);
        subscriber.unSubscribers.push('${accounts}');

    </script>
    <table id="pageView" style="width: 100%; height: 100%; border-collapse: collapse" border="1">
        <tr>
            <td rowspan="2" style="width: 40%; vertical-align: top">
                <div id="transaction" style="padding: 2pt; height: 100%; max-height: 100%; overflow-y: scroll">
                    <div v-for="date in getDates()">
                        {{new Date(date).toLocaleDateString()}}
                        <div style="padding-left: 16pt">
                            <item-view v-for="t in getItemsByDate(date)" :item="t" v-on:click="edit(t.id)"></item-view>
                        </div>
                    </div>

                </div>
            </td>
            <td rowspan="2" style="width: 25%; vertical-align: top">
                <jsp:include page="fastTransactions.jsp" flush="true"/>
            </td>
            <td style="vertical-align: top">
                <jsp:include page="accounts.jsp"/>
            </td>
        </tr>
        <tr>
            <td style="vertical-align: top">
<%--                <jsp:include page="../budget/buyList.jsp"/>--%>
            </td>
        </tr>
    </table>

</html>
