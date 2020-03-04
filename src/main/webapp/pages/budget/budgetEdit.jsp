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
            budget:{
                title:'',
                type:-1,
                amount:0
            }
        },
        methods:{
            save:function(){
                PostApi(this.api.save, this.budget, function(a){
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
    <c:choose>
    <c:when test="${not empty budget}">
    edit.budget = ${budget.toJson()}
    </c:when>
    <c:otherwise>
    edit.budget.type = edit.types[0].id;
    </c:otherwise>
    </c:choose>

</script>
<html>
<table id="editor">
    <tr>
        <td colspan="2">
            <label for="name">
                <fmt:message key="budget.name"/>
            </label>
        </td>
    </tr>
    <tr>
        <td colspan="2">
            <input id="name" v-model="budget.title" autocomplete="off">
        </td>
    </tr>
    <tr>
        <td>
            <label for="type">
                <fmt:message key="budget.type"/>
            </label>
        </td>
        <td>
            <select id="type" v-model="budget.type">
                <option v-for="t in types" :value="t.id">
                    {{t.name}}
                </option>
            </select>
        </td>
    </tr>
    <tr>
        <td>
            <label for="sum">
                <fmt:message key="budget.sum"/>
            </label>
        </td>
        <td>
            <input id="sum" v-model="budget.amount " autocomplete="off">
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
