<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 07.07.2020
  Time: 09:03
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/buyListEdit.vue"></script>
<script>
    buyListEdit.api.save = '${save}';
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
            <span v-if="!editItem" class="text-button" v-on:click="editItem = true">
                <fmt:message key="buy.list.add"/>
            </span>
        </td>
    </tr>
    <tr v-if="editItem" >
        <td>
            <div style="border: solid gray 1px; font-size: 10pt">
                <div>
                    <label for="itemName">
                        <fmt:message key="buy.list.item.title"/>
                    </label>
                </div>
                <div>
                    <input id="itemName" v-model="newItem.title" autocomplete="off" onfocus="this.select()">
                </div>
                <div>
                    <label for="count">
                        <fmt:message key="buy.list.count"/>
                    </label>
                    <div style="width: 60px; display: inline-block">
                        <input id="count" v-model="newItem.count" autocomplete="off" onfocus="this.select()">
                    </div>
                </div>
                <div>
                    <label for="price">
                        <fmt:message key="transaction.price"/>
                    </label>
                    <div style="width: 60px; display: inline-block">
                        <input id="price" v-model="newItem.price" autocomplete="off" onfocus="this.select()">
                    </div>
                </div>
                <div style="text-align: center">
                    <span class="text-button" v-on:click="editItem = false">
                        <fmt:message key="button.cancel"/>
                    </span>
                    <span class="text-button" v-on:click="addItem()">
                        <fmt:message key="button.save"/>
                    </span>
                </div>
            </div>
        </td>
    </tr>
    <tr>
        <td style="height: 120pt">
            <div class="item-container">
                <div v-for="(item, idx) in list.items">
                    <span class="text-button" v-on:click="removeItem(idx)">
                        &times;
                    </span>
                    <template>
                        {{item.title}}
                    </template>
                    <template v-if="item.count > 0 && item.price > 0">
                        {{item.count.toLocaleString()}} &times; {{item.price.toLocaleString()}}
                    </template>
                </div>
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