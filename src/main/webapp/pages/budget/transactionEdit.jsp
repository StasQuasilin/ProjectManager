<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 26.02.2020
  Time: 15:41
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
            budgets:[],
            transaction:{
                type:'',
                date:new Date().toISOString().substring(0, 10),
                sum:0,
                category:{
                    id:-1,
                    name:''
                },
                budget:{
                    id:-1
                },
                payer:{
                    id:-1
                },
                comment:''
            }
        },
        methods:{
            save:function(){
                var data = Object.assign({}, this.transaction);
                data.budget = data.budget.id;
                if (!data.id){
                    data.id = -1;
                }
                PostApi(this.api.save, data, function(a){
                    console.log(a);
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        }
    });
    edit.api.save = '${save}';
    edit.api.findCategory = '${findCategory}';
    <c:forEach items="${types}" var="type">
    edit.types.push({
        id:'${type}',
        name:'<fmt:message key="transaction.${type}"/>'
    });
    </c:forEach>

    <c:forEach items="${budgets}" var="budget">
    edit.budgets.push(${budget.toJson()});
    </c:forEach>
    <c:choose>
    <c:when test="${not empty transaction}">
    edit.transaction = ${transaction.toJson()};
    edit.transaction.sum = Math.abs(edit.transaction.sum);
    </c:when>
    <c:otherwise>
    edit.transaction.type = edit.types[0].id;
    edit.transaction.budget = edit.budgets[0];
    </c:otherwise>
    </c:choose>
</script>
<html>
    <table id="editor">
        <tr>
            <td>
                <span>
                    <fmt:message key="transaction.date"/>
                </span>
                <span>
                    {{new Date(transaction.date).toLocaleDateString()}}
                </span>
            </td>
            <td>
                <div>
                    <span v-for="t in types" v-on:click="transaction.type = t.id">
                        {{t.name}}
                    </span>
                </div>
                <div v-if="transaction.type === 'income' || transaction.type === 'outcome'">
                    <div>
                        <span>
                            <label for="sum">
                                <fmt:message key="payment.edit.sum"/>
                            </label>
                        </span>
                        <span v-if="transaction.type=='income'">
                            +
                        </span>
                        <span v-else>
                            -
                        </span>
                        <input id="sum" v-model="transaction.sum">
                    </div>
                    <div>
                        <label for="category">
                            <fmt:message key="payment.edit.category"/>
                        </label>
                        <input id="category" v-model="transaction.category.name" autocomplete="off">
                    </div>
                    <div>
                        <select v-model="transaction.budget.id">
                            <option v-for="budget in budgets" :value="budget.id">
                                {{budget.title}}
                            </option>
                        </select>
                    </div>
                </div>
                <div v-else-if="transaction.type === 'transfer'">
                    TRANSFER
                </div>
                <div v-else>
                    DEBT
                </div>
                <div>
                   <button onclick="closeModal()">
                       <fmt:message key="buttons.cancel"/>
                   </button>
                    <button v-on:click="save">
                        <fmt:message key="buttons.save"/>
                    </button>
                </div>
            </td>
        </tr>
    </table>
</html>
