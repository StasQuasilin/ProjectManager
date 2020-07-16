<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>

<script>

</script>
<table id="transactions" class="full-size">
    <tr>
        <td>
            <fmt:message key="title.transactions"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </td>
    </tr>
    <tr>
        <td class="full-size">
            <div class="full-size item-container">
                <div v-for="item in getItems()" v-on:click="edit(item.id)">
                    <div>
                        {{item.date}}/ {{item.category.title}}: {{item.amount}} {{item.currency}}
                    </div>
                    <div>
                        {{item.accountFrom.title}}
                    </div>
                    <%--<div>--%>
                        <%--{{new Date(item).toLocaleDateString()}} {{item.type}}--%>
                    <%--</div>--%>
                    <%--<div>--%>
                        <%--{{item.title}}--%>
                    <%--</div>--%>
                </div>
            </div>
        </td>
    </tr>
</table>