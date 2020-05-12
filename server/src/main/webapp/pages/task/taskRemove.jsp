<%--
  Created by IntelliJ IDEA.
  User: szpt-user045
  Date: 03.04.20
  Time: 15:16
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="messages"/>
<html>
    <table>
        <tr>
            <td>
                <button onclick="closeModal()">
                    <fmt:message key="buttons.cancel"/>
                </button>
                <button>
                    <fmt:message key="button.remove"/>
                </button>
            </td>
        </tr>
    </table>
</html>
