<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <table id="buyList" style="width: 100%; height: 100%">
        <tr>
            <td class="transaction-title">
                <span>
                    <fmt:message key="buy.list.title"/>
                </span>
                <span class="mini-button" v-on:click="editList()" v-if="editableItem === -1">
                    + <fmt:message key="button.add"/>
                </span>
            </td>
        </tr>
        <tr>
            <td style="height: 100%; width: 100%; ">
                <div style="overflow-y: scroll; height: 100%">
                    <div v-for="(item, key) in items">
                        <div v-if="key === editableItem">
                            <div class="small-title">
                                <fmt:message key="buy.list.create"/>
                            </div>
                            <div>
                                <label for="title">
                                    <fmt:message key="buy.list.name"/>
                                </label>
                                <input ref="listName" id="title" v-model="editableName" onfocus="this.select()" autocomplete="off">
                            </div>
                            <div style="width: 100%; text-align: center">
                                <span class="mini-button" v-on:click="cancelListEdit()">
                                    <fmt:message key="buttons.cancel"/>
                                </span>
                                <span class="mini-button" v-on:click="saveList()">
                                    <fmt:message key="buttons.save"/>
                                </span>
                            </div>
                        </div>
                        <div v-else>
                            <span v-if="item.open">
                                -
                            </span>
                            <span v-else>
                                +
                            </span>
                            <span v-on:click="openItem(item)">
                                {{item.title}}
                            </span>
                            <span class="mini-button" v-on:click="editList(key)">
                                Edt
                            </span>
                            <div v-if="item.open" style="padding-left: 8pt">
                                <div v-for="element in item.items">
                                    {{element}}
                                </div>
                                <div v-if="item.add">
                                    ADDDDDDDDDDDDD
                                    <span class="mini-button" v-on:click="addElement(item)">
                                        &times;
                                    </span>
                                </div>
                                <div style="width: 100%; text-align: center">
                                    <span class="mini-button" v-on:click="addElement(item)">
                                        <fmt:message key="add.item"/>
                                    </span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </td>
        </tr>
    </table>
</html>