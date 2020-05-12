<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 10.03.20
  Time: 14:11
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<script>
    let id = ${task.id};
    let removeApi = '${remove}';
    function remove() {
        PostApi(removeApi, {id:id}, function(a){
            if (a.status === 'success'){
                closeModal();
            }
        })
    }
</script>
<html>
    <table>
        <tr>
            <td>

            </td>
        </tr>
        <tr>
            <td align="center">
                <button onclick="closeModal()">
                    <fmt:message key="buttons.cancel"/>
                </button>
                <button onclick="remove()">
                    <fmt:message key="button.remove"/>
                </button>
            </td>
        </tr>
    </table>
</html>
