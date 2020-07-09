<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>

<script>
    transactionsList.items[1] = (1);
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
                <div v-for="item in getItems()">
                    {{item}}
                </div>
            </div>
        </td>
    </tr>
</table>