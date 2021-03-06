<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 07.07.2020
  Time: 09:03
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<script src="${context}/vue/buyListEdit.vue"></script>
<script>
    buyListEdit.api.save = '${save}';
    <c:if test="${not empty list}">
    buyListEdit.list = ${list.toJson()}
    </c:if>
</script>
<table id="buyListEdit" class="full-size">
  <tr>
      <td>
          <label for="title">
              <fmt:message key="buy.list.title"/>
          </label>
      </td>
  </tr>
    <tr>
        <td>
            <input id="title" v-model="list.title" autocomplete="off" onfocus="this.select()">
        </td>
    </tr>
    <tr>
        <td style="font-size: 10pt">
            <fmt:message key="buy.list"/>
            <span v-if="!edit" class="text-button" v-on:click="editItem()">
                <fmt:message key="buy.list.add"/>
            </span>
        </td>
    </tr>
    <tr>
        <td style="height: 120pt">
            <div class="item-container">
                <template v-for="(item, idx) in list.items">
                    <div v-if="idx === editableIndex && edit" style="border: solid gray 1px; font-size: 10pt">
                        <div>
                            <input v-model="newItem.title" autocomplete="off" onfocus="this.select()">
                        </div>
                        <div>
                            <label for="count">
                                <fmt:message key="buy.list.count"/>
                            </label>
                            <input id="count" v-model="newItem.count" autocomplete="off" onfocus="this.select()">
                        </div>
                        <div>
                            <label for="price">
                                <fmt:message key="transaction.price"/>
                            </label>
                            <input id="price" v-model="newItem.price" autocomplete="off" onfocus="this.select()">
                        </div>
                        <div style="width: 100%; text-align: center">
                            <span class="text-button" v-on:click="addItem()">
                                &check;
                            </span>
                            <span class="text-button" v-on:click="edit = false">
                                &times;
                            </span>
                        </div>
                    </div>
                    <div v-else>
                       <span v-if="item.done" style="color: green">
                           &check;
                       </span>
                        <span v-else class="text-button" v-on:click="removeItem(idx)">
                            &times;
                        </span>
                        <span v-on:click="editItem(idx)">
                        <template>
                            {{item.title}}
                        </template>
                        <template v-if="item.count > 0 && item.price > 0">
                            {{item.count.toLocaleString()}} &times; {{item.price.toLocaleString()}}
                        </template>
                    </span>
                    </div>
                </template>

            </div>
            <div v-if="!edit">
                <input id="itemName" v-model="newItem.title" autocomplete="off"
                       onfocus="this.select()" style="width: auto">
                <span class="text-button" v-on:click="addItem()">
                    &check;
                </span>
                <span class="text-button" v-on:click="edit = false">
                    &times;
                </span>
            </div>
        </td>
    </tr>
    <tr>
        <td style="text-align: center">
            <button onclick="closeModal()">
                <fmt:message key="button.cancel"/>
            </button>
            <button v-on:click="save()">
                <fmt:message key="button.save"/>
            </button>
        </td>
    </tr>
</table>