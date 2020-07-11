
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 05.07.2020
  Time: 22:22
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:bundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="${context}/vue/goals/goalEdit.vue"></script>
<script>
  goalEdit.api.save = '${save}';
  <c:choose>
  <c:when test="${not empty goal}">
  goalEdit.goal = ${goal.toJson()};
  </c:when>
  </c:choose>
</script>
<div>
  <table id="goalEdit">
    <tr>
      <td colspan="2">
        <label for="title">
          <fmt:message key="goal.title"/>
        </label>
        <input id="title" autocomplete="off">
      </td>
    </tr>
    <tr v-if="!useBeginDate">
      <td colspan="2">
        <span class="text-button">
          <fmt:message key="deal.use.begin"/>
        </span>
      </td>
    </tr>
    <tr>
      <td colspan="2" style="text-align: center">
        <button onclick="closeModal()">
          <fmt:message key="button.cancel"/>
        </button>
        <button v-on:click="save()">
          <fmt:message key="button.save"/>
        </button>
      </td>
    </tr>
  </table>
</div>
