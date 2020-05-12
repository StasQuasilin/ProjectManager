<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 27.02.2020
  Time: 11:43
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<script>
    var edit = new Vue({
        el:'#editor',
        data:{
            api:{},
            types:[],
            account:{
                id:-1,
                title:'',
                type:-1,
                amount:0,
                limit:0,
                currency:-1
            },
            currency:[]
        },
        methods:{
            save:function(){
                PostApi(this.api.save, this.account, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        }
    });
    edit.api.save = '${save}';
    <c:forEach items="${types}" var="type">
    edit.types.push({
        id:'${type}',
        name:'${type}'
    });
    </c:forEach>
    <c:forEach items="${currency}" var="c">
    edit.currency.push(${c.currency.toJson()})
    </c:forEach>
    <c:choose>
    <c:when test="${not empty account}">
    edit.account = ${account.toJson()}
    </c:when>
    <c:otherwise>
    edit.account.type = edit.types[0].id;
    // edit.currency.sort(function (a, b) {
    //     return a.main - b.main;
    // })
    edit.account.currency = edit.currency[0].id;
    </c:otherwise>
    </c:choose>

</script>
<html>
<table id="editor">
    <tr>
        <td colspan="2">
            <label for="name">
                <fmt:message key="account.name"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="name" v-model="account.title" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="type">
                <fmt:message key="account.type"/>
            </label>
        </td>
        <td>
            <select id="type" v-model="account.type">
                <option v-for="t in types" :value="t.id">
                    {{t.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="sum">
                <fmt:message key="account.sum"/>
            </label>
        </td>
        <td>
            <input id="sum" v-model="account.amount " autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="currency">
                <fmt:message key="currency"/>
            </label>
        </td>
        <td>
            <select id="currency" v-model="account.currency">
                <option v-for="c in currency" :value="c.id">
                    {{c.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr v-if="account.type === 'card' || account.type === 'credit'">
        <td>
            <label for="limit">
                <fmt:message key="account.limit"/>
            </label>
        </td>
        <td>
            <input id="limit" v-model="account.limit" autocomplete="off">
        </td>
    </tr>
    <tr v-else-if="account.type === 'deposit'">
        <td colspan="2">
            Deposit settings
        </td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <button onclick="closeModal()">
                <fmt:message key="buttons.cancel"/>
            </button>
            <button v-on:click="save">
                <fmt:message key="buttons.save"/>
            </button>
        </td>
    </tr>
</table>
</html>
