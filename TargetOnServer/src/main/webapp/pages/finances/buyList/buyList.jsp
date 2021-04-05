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
                <span class="text-button" v-on:click="remove(item.id)">
                    &times;
                </span>
            <span>
                {{item.title}}
                <span class="text-button edit-button" v-on:click="edit(item.id)"></span>
            </span>
                <div style="padding-left: 8px; font-size: 10pt">
                    <div v-for="i in item.items" style="display: flex; flex-direction: row">
                        <div style="flex: 0 0 auto">
                            <input type="checkbox" v-model="i.done" v-on:change="changeStatus(i)" :disabled="i.done">
                        </div>
                        <div style="flex: 1 0 auto">
                            <div>
                                <template v-for="(p, i) in getPath(i)" v-if="i > 0">
                                    {{p.title}} /
                                </template>
                                <b>{{i.title}}</b>
                            </div>
                            <div style="color: gray">
                                <span v-if="i.date">
                                    {{new Date(i.date).toLocaleString().substring(0, 10)}}
                                </span>
                                <template v-if="i.count !== 0 || i.price !== 0">
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
                                </template>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
