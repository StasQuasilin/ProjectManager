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
        <fmt:message key="total.balance"/>:
        <span v-for="(b, c) in totalBalance" v-if="b > 0">
            {{b.toLocaleString()}} {{c}}
        </span>
    </div>
    <div class="full-size item-container">
        <div class="item-container-wrapper">
            <div v-for="(items, key) in accountMap">
                <div>
                    {{key}}
                </div>
                <div v-for="item in items" class="account-item" :class="item.type" style="padding-left: 18pt">
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
</div>