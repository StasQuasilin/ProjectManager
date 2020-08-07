<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<div id="buyList" class="full-size">
    <div class="cell-header">
        <div class="header-wrapper">
            <fmt:message key="title.buy.list"/>
            <button v-on:click="edit()">
                <fmt:message key="button.add"/>
            </button>
        </div>
    </div>
    <div class="full-size item-container">
        <div class="item-container-wrapper">
            <div v-for="item in getItems()">
            <span>
                {{item.title}}
                <span class="text-button" v-on:click="edit(item.id)">
                    &#128397;
                </span>
            </span>
                <div style="padding-left: 8px; font-size: 10pt">
                    <div v-for="i in item.items" style="display: flex; flex-direction: row">
                        <div style="flex: 0 0 auto">
                            <input type="checkbox" v-model="i.done" v-on:change="changeStatus(i)">
                        </div>
                        <div style="flex: 1 0 auto">
                            <div>
                                {{i.title}}
                            </div>
                            <div v-if="i.count !== 0 || i.price !== 0" style="color: gray">
                                <span v-if="i.count !== 0">
                                    {{i.count.toLocaleString()}} {{unitNames[i.unit]}}
                                </span>
                                <span v-if="i.count !== 0 && i.price !== 0">
                                    &times;
                                </span>
                                <span v-if="i.price !== 0">
                                    {{i.price.toLocaleString()}} {{i.currency}}
                                </span>
                                <span v-if="i.count > 1 && i.price !== 0">
                                    = {{(i.count * i.price).toLocaleString()}} {{i.currency}}
                            </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
