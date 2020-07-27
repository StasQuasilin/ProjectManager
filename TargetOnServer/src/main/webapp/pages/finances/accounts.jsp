<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<table id="accountsList" class="full-size">
    <tr>
        <td>
            <fmt:message key="title.accounts"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </td>
    </tr>
    <tr>
        <td class="full-size">
            <div class="item-container">
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
        </td>
    </tr>
</table>