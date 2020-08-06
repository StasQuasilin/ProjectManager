<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<table id="fastTransactions" class="full-size">
    <tr>
        <td>
            <fmt:message key="title.fast.transactions"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </td>
    </tr>
    <tr>
        <td class="full-size">
            <div class="item-container">

            </div>
        </td>
    </tr>
</table>