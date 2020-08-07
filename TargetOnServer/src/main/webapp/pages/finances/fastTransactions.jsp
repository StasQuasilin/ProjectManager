<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="fastTransactions" class="full-size">
    <div class="cell-header">
        <div class="header-wrapper">
            <fmt:message key="title.fast.transactions"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </div>
    </div>
    <div class="full-size item-container">

    </div>
</div>