<%--
  Created by IntelliJ IDEA.
  User: szpt_user045
  Date: 02.03.2020
  Time: 15:45
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<script>
    var remove = new Vue({
        el:'#remove',
        data:{
            api:{},
            id:-1
        },
        methods:{
            remove:function(){
                PostApi(this.api.remove, {id:this.id}, function(a){
                    if (a.status === 'success'){
                        closeModal();
                    }
                })
            }
        }
    });
    remove.api.remove = '${delete}';
    remove.id = '${project.id}'
</script>
<html>
    <table style="width: 100%" id="remove">
        <tr>
            <td>
                <fmt:message key="project.remove.shure?"/>
            </td>
        </tr>
        <tr>
            <td align="center">
                <button onclick="closeModal()">
                    <fmt:message key="buttons.no"/>
                </button>
                <button v-on:click="remove">
                    <fmt:message key="buttons.yes"/>
                </button>
            </td>
        </tr>
    </table>
</html>
