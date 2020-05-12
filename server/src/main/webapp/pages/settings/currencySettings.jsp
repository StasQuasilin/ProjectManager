<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 11.03.20
  Time: 09:31
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<script>
    var edit = new Vue({
        el:'#edit',
        data:{
            api:{},
            userCurrency:[],
            currency:[],
            add:false
        },
        methods:{
            addCurrency:function(currency){
                Vue.set(currency, 'main', this.userCurrency.length === 0);
                this.userCurrency.push(currency);
                this.add = false;
            },
            switchMain:function(currency){
                const self = this;
                this.userCurrency.forEach(function (item) {
                    if (item.id === currency.id){
                        item.main = true;
                        self.saveItem(item);
                    } else if (item.main){
                        item.main = false;
                        self.saveItem(item);
                    }
                });
                console.log(currency.name + ' --> ' + currency.main);
            },
            saveItem:function(item){
                PostApi(this.api.save, item, function(a){
                    console.log(a);
                })
            }
        }
    });
    edit.api.save = '${currencyEdit}';
    edit.api.remove = '${currencyRemove}';
    <c:forEach items="${currency}" var="c">
    edit.currency.push(
        ${c.toJson()}
    );
    </c:forEach>
</script>
<html>
    <div id="edit">

        <div v-for="u in userCurrency">
            <input :id="u.id" type="radio" name="main" :value="u.main" v-model="u.main" v-on:change="switchMain(u)">
            <span v-if="!u.main">
                &times;
            </span>
            <span v-else>
                &nbsp;
            </span>
            <label :for="u.id">
                {{u}}
            </label>
        </div>
        <template v-if="add">
            <div  v-for="c in currency" v-on:click="addCurrency(c)">
                {{c}}
            </div>
        </template>
        <div v-else>
            <button v-on:click="add=true">
                <fmt:message key="currency.add"/>
            </button>
        </div>
    </div>
    <button onclick="closeModal()">
        <fmt:message key="buttons.cancel"/>
    </button>
</html>
