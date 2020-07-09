<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<table id="accountsList" class="full-size">
    <tr>
        <td>
            <fmt:message key="title.account"/>
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
                        {{item.sum.toLocaleString()}}
                    </div>
                </div>
            </div>
        </td>
    </tr>
</table>