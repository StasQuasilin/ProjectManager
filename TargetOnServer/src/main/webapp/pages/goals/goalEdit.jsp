
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 05.07.2020
  Time: 22:22
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<fmt:setLocale value="${locale}"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script src="${context}/vue/goals/goalEdit.vue"></script>
<script>
  goalEdit.api.save = '${save}';
  <c:forEach items="${currency}" var="c">
  goalEdit.currency.push('${c}');
  </c:forEach>
  <c:choose>
  <c:when test="${not empty goal}">
  goalEdit.goal = ${goal.toJson()};
  if (goalEdit.goal.begin){
    goalEdit.useBeginDate = true;
    if (goalEdit.goal.end){
      goalEdit.useEndDate = true;
    }
  }
  </c:when>
  </c:choose>
  if (!goalEdit.goal.currency){
    goalEdit.goal.currency = goalEdit.currency[0];
  }
</script>
<div>
  <table id="goalEdit">
    <tr>
      <td colspan="2">
        <label for="title">
          <fmt:message key="goal.title"/>
        </label>
        <input id="title" v-model="goal.title" autocomplete="off" onfocus="this.select()">
      </td>
    </tr>
    <tr v-if="!useBeginDate">
      <td colspan="2">
        <span class="text-button" v-on:click="useBeginDate = true">
          <fmt:message key="goal.use.begin"/>
        </span>
      </td>
    </tr>
    <tr v-else>
      <td>
        <fmt:message key="goal.begin.date"/>
      </td>
      <td>
        <span class="text-button">
          {{new Date(goal.begin).toLocaleDateString()}}
        </span>
        <span class="text-button" v-on:click="useBeginDate = false">
          &times;
        </span>
      </td>
    </tr>
    <template v-if="useBeginDate">
      <tr v-if="!useEndDate">
        <td colspan="2">
          <span class="text-button" v-on:click="useEndDate = true">
            <fmt:message key="goal.use.end"/>
          </span>
        </td>
      </tr>
      <tr v-else>
        <td>
          <fmt:message key="goal.end.date"/>
        </td>
        <td>
          <span class="text-button">
            {{new Date(goal.end).toLocaleDateString()}}
          </span>
          <span class="text-button" v-on:click="useEndDate = false">
            &times;
          </span>
        </td>
      </tr>
    </template>
    <tr>
      <td>
        <label for="budget">
          <fmt:message key="goal.budget"/>
        </label>
      </td>
      <td>
        <input id="budget" type="number" v-model="goal.budget" autocomplete="off" onfocus="this.select()"
          style="width: 72pt">
        <select id="currency" v-model="goal.currency">
          <option v-for="c in currency">
            {{c}}
          </option>
        </select>
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
