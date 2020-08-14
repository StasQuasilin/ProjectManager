<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="transactions" class="full-size">
    <div class="cell-header">
        <div class="header-wrapper">
            <fmt:message key="title.transactions"/>
            <button v-on:click="tryEdit()">
                <fmt:message key="button.add"/>
            </button>
            <span class="text-button" style="float: right; color: gray" v-on:click="useFilter = !useFilter">
                <fmt:message key="filter"/>
            </span>
        </div>
    </div>
    <div class="full-size item-container">
        <div class="item-container-wrapper">
            <div v-for="(values, date) in compoundItems">
                <div style="font-size: 10pt; font-weight: bold">{{new Date(date).toLocaleDateString()}}</div>
                <div style="padding-left: 32px">
                    <div v-for="item in values"  v-on:click="edit(item.id)" style="border-bottom: dashed gray 1px">
                        <div style="font-size: 8pt; color: gray">
                            {{item.id}}:{{item.type}}
                        </div>
                        <div>
                            <span v-if="item.category">
                                {{item.category.title}}
                            </span>
                            <span v-else style="color: gray">
                                <fmt:message key="transaction.without.category"/>
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
    </div>
</div>
