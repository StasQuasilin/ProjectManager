
<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 05.07.2020
  Time: 22:22
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="messages"/>
<%@ page contentType="text/html;charset=UTF-8" %>
<script type="application/javascript" src="${context}/vue/templates/inputWithSearch.vue"></script>
<script type="application/javascript" src="${context}/vue/templates/datetime/datePicker.vue"></script>
<script type="application/javascript" src="${context}/vue/goals/goalEdit.vue"></script>
<script>
  goalEdit.api.save = '${save}';
  goalEdit.api.goalMembers = '${goalMembers}';
  goalEdit.buyListProps.findCategory = '${findBuyList}';
  <c:forEach items="${currency}" var="c">
  goalEdit.currency.push('${c}');
  </c:forEach>
  <c:forEach items="${members}" var="member">
  goalEdit.members.push(${member.member.toJson()});
  </c:forEach>
  <c:if test="${not empty goal}">
  goalEdit.goal = ${goal.toJson()};
  <c:if test="${not empty buyList}">
  goalEdit.buyList = ${buyList.shortJson()};
  goalEdit.useBuyList = true;
  </c:if>
  </c:if>
  now = new Date();
  end = new Date();
  end.setMonth(end.getMonth() + 1);
  if (goalEdit.goal.begin){
    goalEdit.useBeginDate = true;
    if (goalEdit.goal.end){
      goalEdit.useEndDate = true;
    } else {
      goalEdit.goal.end = end.toISOString().substring(0, 10);
    }
  } else {
    goalEdit.goal.begin = now.toISOString().substring(0, 10);
    goalEdit.goal.end = end.toISOString().substring(0, 10);
  }
  if (!goalEdit.goal.currency){
    goalEdit.goal.currency = goalEdit.currency[0];
  }
</script>
<div>
  <table id="goalEdit">
    <tr :class="{error : errors.title}">
      <td colspan="2">
        <label for="title">
          <fmt:message key="goal.title"/>
        </label>
        <input id="title" v-model="goal.title" autocomplete="off" v-on:click="errors.title = false"
               onfocus="this.select()">
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
        <date-picker :date="goal.begin" :props="beginDateProps"></date-picker>
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
          <date-picker :date="goal.end" :props="endDateProps"></date-picker>
          <span class="text-button" v-on:click="useEndDate = false">
            &times;
          </span>
        </td>
      </tr>
    </template>
    <template v-if="useBeginDate && useEndDate">
      <tr>
        <td>
          <fmt:message key="goal.duration"/>
        </td>
        <td>
          <span>
            <span v-if="goalDuration.year > 0">
              {{goalDuration.year.toLocaleString()}}
              <template v-if="goalDuration.year === 1">
                <fmt:message key="1.year"/>
              </template>
              <template v-else-if="goalDuration.year < 5">
                <fmt:message key="2.years"/>
              </template>
              <template v-else>
                <fmt:message key="5.years"/>
              </template><span v-if="goalDuration.month > 0">
                ,
              </span>
            </span>
            <template v-if="goalDuration.month > 0">
              {{goalDuration.month.toLocaleString()}}
              <template v-if="goalDuration.month === 1">
                <fmt:message key="1.month"/>
              </template>
              <template v-else-if="goalDuration.month < 5">
                <fmt:message key="2.months"/>
              </template>
              <template v-else>
                <fmt:message key="5.months"/>
              </template>
            </template>
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
        <input id="budget" type="number" min="0" v-model="goal.budget" autocomplete="off" onfocus="this.select()"
          style="width: 72pt">
        <select id="currency" v-model="goal.currency">
          <option v-for="c in currency">
            {{c}}
          </option>
        </select>
      </td>
    </tr>
    <tr v-if="!useBuyList">
      <td colspan="2">
        <span class="text-button" v-on:click="useBuyList = true">
          <fmt:message key="use.buy.list"/>
        </span>
      </td>
    </tr>
    <tr v-else :class="{error : errors.buyList}">
      <td>
        <fmt:message key="buy.list"/>
      </td>
      <td>
        <span v-if="separatedBuyList" v-on:click="separatedBuyList=false">
          <fmt:message key="goal.separated.buy.list"/>
        </span>
        <template v-else >
          <div v-on:click="errors.buyList = false">
            <find-input :object="buyList" :props="buyListProps"></find-input>
          </div>
          <div style="font-size: 10pt">
            <span class="text-button" v-on:click="separatedBuyList=true">
              <fmt:message key="goal.separated.buy.list"/>
            </span>
            <span class="text-button" v-on:click="useBuyList = false">
              &times;
            </span>
          </div>
        </template>
      </td>
    </tr>
    <tr>
      <td colspan="2" style="text-align: right">
        <span class="text-button" v-on:click="memberList()">
          <fmt:message key="goal.members"/>: {{(members.length + 1).toLocaleString()}}
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
