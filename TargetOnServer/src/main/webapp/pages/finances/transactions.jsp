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
            <span class="text-button" style="float: right; color: gray" v-on:click="useFilter = !useFilter">
                <fmt:message key="filter"/>
            </span>
        </td>
    </tr>
    <template v-if="useFilter">
        <tr>
            <td colspan="2">
                Filter be here!
            </td>
        </tr>
    </template>
    <tr>
        <td class="full-size">
            <div class="full-size item-container">
                <div v-for="item in getItems()" v-on:click="edit(item.id)">
                    <div style="font-size: 8pt; color: gray">
                        {{item.id}}:{{item.type}}
                    </div>
                    <div>
                        <span>{{new Date(item.date).toLocaleDateString()}}</span>
                        <span v-if="item.category">
                            {{item.category.title}}
                        </span>
                    </div>
                    <div>
                        <span v-if="item.accountFrom">
                            {{item.accountFrom.title}} &#10141;
                        </span>
                        <span>
                            <template v-if="item.type === 'income' || item.type === 'spending'">
                                <template v-if="item.type === 'income'">
                                    +
                                </template>
                                <template v-else>
                                    -
                                </template>
                            </template>
                            {{Math.abs(item.amount).toLocaleString()}} {{item.currency}}
                        </span>
                        <span v-if="item.accountTo">
                            &#10141; {{item.accountTo.title}}
                        </span>
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