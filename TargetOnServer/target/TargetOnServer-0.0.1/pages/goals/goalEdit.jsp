
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 05.07.2020
  Time: 22:22
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:bundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
  <table>
    <tr>
      <td colspan="2">
        <label for="title">
          <fmt:message key="goal.title"/>
        </label>
        <input id="title" autocomplete="off">
      </td>
    </tr>
    <tr>
      <td>

      </td>
    </tr>
  </table>
</div>
