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
    <div class="full-size item-container">
        <div class="item-container-wrapper">
            <div v-for="item in getItems()" v-on:click="edit(item.id)">
                <div>
                    {{item.title}}
                    ( {{item.type}} )
                </div>
                <div>
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