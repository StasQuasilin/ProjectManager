<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<table id="buyList" class="full-size">
    <tr>
        <td>
            <fmt:message key="title.buy.list"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </td>
    </tr>
    <tr>
        <td class="full-size">
            <div class="item-container">
                <div v-for="item in getItems()">
                    <span>
                        {{item.title}}
                        <span class="text-button" v-on:click="edit(item.id)">
                            <fmt:message key="button.edit"/>
                        </span>
                    </span>
                    <div style="padding-left: 8px">
                        <div v-for="i in item.items">
                            {{i.title}} {{i.count}} &times; {{i.price}}
                        </div>
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>