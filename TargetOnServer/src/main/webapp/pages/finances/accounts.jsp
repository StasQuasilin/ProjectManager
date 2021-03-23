<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="accountsList" class="full-size">
    <div class="cell-header">
        <div class="header-wrapper">
            <fmt:message key="title.accounts"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </div>
    </div>
    <div>
        <fmt:message key="total.balance"/>:{{totalBalance.toLocaleString()}}
        <fmt:message key="personal.balance"/>:{{personalBalance.toLocaleString()}}
    </div>
    <div class="full-size item-container">
        <div class="item-container-wrapper">
            <div v-for="item in getItems()" class="account-item" :class="item.type">
                <div class="account-title">
                    {{item.title}}
                    <span class="account-menu" style="padding: 4px; background-color: gray; border-radius: 8pt">
                        <div class="text-button edit-button" v-on:click="edit(item.id)"></div>
<%--                        <span class="text-button" v-on:click="accountExtract(item.id)">--%>
<%--                            R--%>
<%--                        </span>--%>
                    </span>
                </div>
                <div style="font-size: 10pt; color: gray; padding-left: 16px">
                    <span>
                        {{(item.sum + item.limit).toLocaleString()}}
                    </span>
                    <span v-if="item.limit">
                        ( {{item.sum.toLocaleString()}} + {{item.limit.toLocaleString()}} )
                    </span>
                    <span>
                        {{item.currency}}
                    </span>
                </div>
            </div>
        </div>
    </div>
</div>