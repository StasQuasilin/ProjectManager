<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 2021-02-25
  Time: 11:04
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<html>
    <script>
        function deleteIt() {
            PostApi('${delete}', {id:${task.id}}, function (a) {
                console.log(a);
                if (a.status === 'success'){
                    closeModal();
                }
            })
        }
    </script>
    <fmt:message key="task.delete.q"/>
    '${task.title}'?
    <div style="width: 100%; text-align: center">
        <button onclick="closeModal()">
            <fmt:message key="button.cancel"/>
        </button>
        <button onclick="deleteIt()">
            <fmt:message key="button.remove"/>
        </button>
    </div>
</html>
