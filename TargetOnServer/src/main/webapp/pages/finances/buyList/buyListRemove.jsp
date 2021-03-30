
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-03-30
  Time: 15:16
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
<script>
    id = ${list.id};
    api = '${delete}';
    function remove() {
        PostApi(api, {id:id}, function (a) {
            if (a.status === 'success'){
                closeModal();
            }
        })
    }
</script>
<fmt:message key="buy.list.remove"/> '${list.title.value}'?
<div style="width: 100%; text-align: center">
    <button onfocus="closeModal()">
        <fmt:message key="button.cancel"/>
    </button>
    <button onclick="remove()">
        <fmt:message key="button.remove"/>
    </button>
</div>
</html>
