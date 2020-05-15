<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <table id="buyList" style="width: 100%; height: 100%">
        <tr>
<<<<<<< HEAD
            <td class="transaction-title">
                <span>
                    <fmt:message key="buy.list.title"/>
                </span>
                <span class="mini-button" v-on:click="editList()" v-if="editableItem === -1">
=======
            <td style="text-align: center">
                <span>
                    <fmt:message key="buy.list.title"/>
                </span>
                <span class="mini-button" v-on:click="editList" v-if="editableItem === -1">
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
                    + <fmt:message key="button.add"/>
                </span>
            </td>
        </tr>
        <tr>
            <td style="height: 100%; width: 100%; ">
<<<<<<< HEAD
                <div style="overflow-y: scroll; height: 100%">
=======
                <div style="border: solid 1pt; overflow-y: scroll; height: 100%">
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
                    <div v-for="(item, key) in items">
                        <div v-if="key === editableItem">
                            <div class="small-title">
                                <fmt:message key="buy.list.create"/>
                            </div>
                            <div>
                                <label for="title">
                                    <fmt:message key="buy.list.name"/>
                                </label>
<<<<<<< HEAD
                                <input ref="listName" id="title" v-model="editableName" onfocus="this.select()" autocomplete="off">
=======
                                <input id="title" v-model="editableName" onfocus="this.select()" autocomplete="off">
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
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
<<<<<<< HEAD
                        <div v-else>
=======
                        <div v-else v-on:click="openItem(item)">
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
                            <span v-if="item.open">
                                -
                            </span>
                            <span v-else>
                                +
                            </span>
<<<<<<< HEAD
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
=======
                            <span>
                                {{item.title}}
                            </span>
                            <div v-if="item.open">
                                <div style="width: 100%; text-align: center">
                                    <span class="mini-button">
>>>>>>> 52c5a1d19c9129afc96e83fc384e8c2354a91ca9
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