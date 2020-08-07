<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="transactions" class="full-size">
    <div class="cell-header">
        <div class="header-wrapper">
            <fmt:message key="title.transactions"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
            <span class="text-button" style="float: right; color: gray" v-on:click="useFilter = !useFilter">
                <fmt:message key="filter"/>
            </span>
        </div>
    </div>
    <div class="full-size item-container">
        <div class="item-container-wrapper">
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
                    <b>
                        <template v-if="item.type === 'income' || item.type === 'spending'">
                            <template v-if="item.type === 'income'">
                                +
                            </template>
                            <template v-else>
                                -
                            </template>
                        </template>
                        {{Math.abs(item.amount).toLocaleString()}} {{item.currency}}
                    </b>
                    <span v-if="item.accountTo">
                    &#10141; {{item.accountTo.title}}
                </span>
                </div>
            </div>
        </div>

    </div>
</div>
